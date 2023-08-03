package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import org.json.JSONObject
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherAdapter
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherModel
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentHoursBinding
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentMainBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
        // return inflater.inflate(R.layout.fragment_hours, container, false) я не помню откуда и что это, но удалять на всякий случай не буду
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.LiveDataCurrent.observe(viewLifecycleOwner){//app server
            adapter.submitList(getHoursList(it))
        }
    }

    private fun initRcView() =
        with(binding) { //с помощью этого мы получаем доступ к элементам экрана
            rcView.layoutManager = LinearLayoutManager(activity)
            adapter = WeatherAdapter()
            rcView.adapter = adapter
        }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel>{
        val hoursArray = JSONArray(wItem.hours)
        val list = ArrayList<WeatherModel>()
        for (i in 0 until hoursArray.length()){
            val item = WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "", "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}

