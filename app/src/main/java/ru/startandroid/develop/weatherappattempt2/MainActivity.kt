package ru.startandroid.develop.weatherappattempt2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fragments.MainFragment

class MainActivity :
    AppCompatActivity() { //майн активити будет использоваться только для запуска фрагментов
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()
    }
}