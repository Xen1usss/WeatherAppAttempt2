package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherAdapter
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherModel
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentDaysBinding

//добавляем сюда листенер из везер адаптер, тк именно здесь нужно прослушивать нажатия
class DaysFragment : Fragment(), WeatherAdapter.Listener {
    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: FragmentDaysBinding
    private val model: MainViewModel by activityViewModels() //это доступ к классу MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.LiveDataList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    private fun init() = with(binding){
        adapter = WeatherAdapter(this@DaysFragment) //просто this ссылался бы просто на байндинг
        rcView.layoutManager = LinearLayoutManager(activity) //активити как контекс здесь
        rcView.adapter = adapter //таким образом подключаем к rcView адаптер
    }

    companion object {

        fun newInstance() = DaysFragment()
                }

    override fun onClick(item: WeatherModel) { //запускается при нажатии на день и возвращает результат
        model.LiveDataCurrent.value = item
    }
}

