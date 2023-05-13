package fragments

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment // что значит импорт класса, возникающий автоматически?

fun Fragment.isPermissionGranted(p: String): Boolean { //проверка на уже имеющееся разрешение
    return ContextCompat.checkSelfPermission(
        activity as AppCompatActivity, p) == PackageManager.PERMISSION_GRANTED
    //возвращает какой-то результат в зависимости от того, есть разрешение или нет
    //как это мы используем интеджер в булине? Как это сказывается на памяти?
}