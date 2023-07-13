package fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.adapters.VpAdapter
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherModel
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentMainBinding

const val API_KEY = "9562bba8a2bd4c1eae0142135232604"

class MainFragment : Fragment() {
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String> //в треуг. скобках тип данных, который передаем
    private lateinit var binding: FragmentMainBinding //переменная, в которой мы будем хранить эту инстанцию
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root // root-элемент - это и есть view (??)
    }

    //функция, когда основной фрагмент загружен, когда все view уже созданы
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //todo add tabs
        checkPermission()
        init()
        updateCurrentCard()
        requestWeatherData("London")
    }

    private fun init() = with(binding) { //в этой функции все инициализируем
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp) { tab, pos ->
            tab.text = tList[pos] // на tab нажимаем, pos - позиция
        }
    }.attach()

    private fun updateCurrentCard() = with(binding) {
        model.LiveDataCurrent.observe(viewLifecycleOwner) {//app сервер, который по умолчанию передает переменную WeatherModel
            val maxMinTemp =
                "${it.maxTemp}C/${it.minTemp}C" //по-другому WeatherModel выше можно прописать "item -->"
            tvData.text = it.time
            tvCity.text = it.city
            tvCurrentTemp.text = it.currentTemp
            tvMaxMin.text = maxMinTemp
            /*что-то с ошибкой */ //coil.get().load(it.imageUrl).into(imWeather)
        }
    }


    //инициализируем/регистрируем pLauncher
    private fun permissionListener() { //проверка на разрешение в реальном времени
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {//проверяем, есть ли разрешение на текущую геопозицию
            permissionListener() //если нет разрешения, регистрируем листенер
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) //то запускаем диалог, чтобы спросить пользователя
            //а если разрешение есть, то ничего не делаем
        }
    }


    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseWeatherData(result)
                Log.d("MyLog", "Result: $result")
            },
            { error ->
                error.printStackTrace()
                Log.e("MyLog", "Error: $error")
            },

            )
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObject =
            JSONObject(result) //MainObject - это основной джсон обджект, внутри которого маленькие джсон обджекты
        val list = parseDays(mainObject)
        parseCurrentData(mainObject, list[0]) // 0 - самый первый элемент, типа сегодняшний день
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> { //функция получения данных для всех нужных дней
        val list = ArrayList<WeatherModel>() //мы создали список, он пока пустой
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                // day.getJSONObject("day ").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()

            )
            list.add(item)
        }
        return list
    }

    private fun parseCurrentData(
        mainObject: JSONObject,
        weatherItem: WeatherModel
    ) { //эта функция исключительно для заполнения сновной карточки
        val item = WeatherModel( //сюда и будем передавать данные
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            //mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
        model.LiveDataCurrent.value = item
    } //функция получения данных для сегодняшнего дня

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
