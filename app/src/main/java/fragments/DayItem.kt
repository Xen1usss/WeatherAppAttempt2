package fragments

data class DayItem( // тут указываем все переменные, которые понадобятся именно для этого куска на экране, для этого класса
    val city: String,
    val time: String,
    val condition: String,
    val imageUrl: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String,
    )
