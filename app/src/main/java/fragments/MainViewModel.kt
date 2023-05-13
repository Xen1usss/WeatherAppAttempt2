package fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() { // добавим сюда две переменные - два объекта
    val LiveDataCurrent = MutableLiveData<String>() //обновляется, когда мы получаем новую информацию, самая верхняя карточка
    val LiveDataList = MutableLiveData<List<String>>() //типа то что там после верхней зоны
}