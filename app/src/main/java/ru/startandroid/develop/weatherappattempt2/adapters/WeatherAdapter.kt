package ru.startandroid.develop.weatherappattempt2.adapters

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.weatherappattempt2.databinding.ListItemBinding

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.Holder> () { //в адаптер передается из каких элементов будет составляться список
    //то есть холдер хранит в себе логику как заполнять элемент и сам элемент
    class Holder(view: View) : RecyclerView.ViewHolder(view) { //в аргументе один вью-элемент который мы передаем и который сохраняется для ресайкл
        val binding = ListItemBinding.bind(view)  //это будущий вью байндинг - класс разметки, который хранит в себе элементы
        fun bind(item: WeatherModel) = with(binding) {  //и здесь данная функция заполняет этот один элемент
            //with позволяет прямо в теле функции прописать присвоение к вью из итем
            tvData.text = item.time
            tvCondition.text = item.condition
            tvTemp.text = item.currentTemp

        }
    }


}