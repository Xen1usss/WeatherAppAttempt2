package ru.startandroid.develop.weatherappattempt2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.databinding.ListItemBinding

class WeatherAdapter : ListAdapter<WeatherModel, WeatherAdapter.Holder> (Comparator()) { //в адаптер передается: элемент из списка и класс, который описывает, как заполнять элемент списка
    //то есть холдер хранит в себе логику как заполнять элемент и сам элемент
    //то есть листАдаптер принимает список, с помощью которого заполняет ресайклер
    class Holder(view: View) : RecyclerView.ViewHolder(view) { //в аргументе один вью-элемент который мы передаем и который сохраняется для ресайкл
        val binding = ListItemBinding.bind(view)  //это будущий вью байндинг - класс разметки, который хранит в себе элементы
        fun bind(item: WeatherModel) = with(binding) {  //и здесь данная функция заполняет этот один элемент
            //with позволяет прямо в теле функции прописать присвоение к вью из итем
            tvData.text = item.time
            //tvCondition.text = item.condition
            tvTemp.text = item.currentTemp
            //вот тут нужно разобраться в библиотеках и добавить имедж примерно как в сточке ниже
            //Picasso(у меня Coil).get().load("https:"+ item.imageUrl)into(im)
            //картинка из карточек во фраменте hours справа
        }
    }

    class Comparator : DiffUtil.ItemCallback<WeatherModel> () { //данный класс автоматом имеет 2 функции
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem //тут желательно сравнивать по уник. ид-ру, но у нас его нет, поэтому вот так вот
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }


    }
//что-то
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { //метод 1 - создает эелемент
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) //создается вью
        return Holder(view)//создается холдер
    } //эта функция будет запускаться столько раз, сколько элементов в списке

    override fun onBindViewHolder(holder: Holder, position: Int) { //метод 2 - заполняет элемент
        // функция выдает сам холдер и позицию, на которой он создался
        holder.bind(getItem(position))
    }

}