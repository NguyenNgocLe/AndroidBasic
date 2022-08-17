import kotlin.math.round

fun main() {
    val value = getIndexVideo(14.9F)
   print(value)
}

private fun getIndexVideo(second: Float): Int {
    var value = 0
    val arrayTimeStart = arrayListOf(
        0.0F,
        0.833F,
        6.179F,
        12.935F,
        14.923F,
        18.244F,
        20.15F,
        24.103F,
        25.804F,
        28.913F,
        31.579F,
        35.197F,
        37.102F,
        40.692F,
        45.776F,
        49.155F,
        52.676F,
        54.343F,
        56.54F,
        59.194F,
        61.208F,
        63.945F,
        66.348F,
        69.487F,
        73.831F,
        76.653F
    )
    arrayTimeStart.forEachIndexed { index, element ->
        value = index
        if (element.round(1) < second && arrayTimeStart[index + 1] > second) {
            return value
        }
    }
    return value
}

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (round(this * multiplier) / multiplier).toFloat()
}