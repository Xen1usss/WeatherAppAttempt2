package fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import ru.startandroid.develop.weatherappattempt2.R
import ru.startandroid.develop.weatherappattempt2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String> //в треуг. скобках тип данных, который передаем
    private lateinit var binding: FragmentMainBinding //переменная, в которой мы будем хранить эту инстанцию

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
        checkPermission()
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

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
