package com.ochokom.project3.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DodgerBlue = Color(0xFF1E90FF)
val WhiteBlue = Color(0xFFDCEEF1)
val WhiteGreen = Color(0xFFC6F3EC)

val DodgerDark = Color(0xFF092D50)
val BlackBlue = Color(0xFF172124)
val BlackGreen = Color(0xFF203634)


fun hexColor(hex: String): Color {
    var hexStr = hex
    if (hex[0] != '#') {
        hexStr = "#$hex"
    }
    return try {
        Color(android.graphics.Color.parseColor(hexStr))
    } catch (e: IllegalArgumentException) {
        Color.Black
    }
}