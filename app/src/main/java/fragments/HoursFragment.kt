package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherAdapter
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherModel
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentHoursBinding
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentMainBinding


class HoursFragment : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

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
    }

    private fun initRcView() = with(binding) { //с помощью этого мы получаем доступ к элементам экрана
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val list = listOf(

            WeatherModel("", "12:00",  "25°C", "", "", "", ""), // здесь тоже был кондишн
            WeatherModel("", "13:00",  "27°C", "", "", "", ""),
            WeatherModel("", "14:00",  "35°C", "", "", "", "")

        )
        adapter.submitList(list) //загружаем список
    }


    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}

