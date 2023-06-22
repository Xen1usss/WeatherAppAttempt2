package ru.startandroid.develop.weatherappattempt2.adapters

data class WeatherModel(
    //те - группа/образец переменных
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String,
)

