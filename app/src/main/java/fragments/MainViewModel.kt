package fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.startandroid.develop.weatherappattempt2.adapters.WeatherModel

class MainViewModel : ViewModel() { // добавим сюда две переменные - два объекта
    val LiveDataCurrent = MutableLiveData<WeatherModel>() //обновляется, когда мы получаем новую информацию, самая верхняя карточка
    val LiveDataList = MutableLiveData<List<WeatherModel>>() //сюда передастся список, который мы получаем в мейн фрагменте
}