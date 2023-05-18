package com.mikyegresl.valostat.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.mikyegresl.valostat.R

val bowlyOne = FontFamily(
    Font(resId = R.font.bowlby_one, weight = FontWeight.W400)
)

val drukWide = FontFamily(
    Font(resId = R.font.druk_wide, weight = FontWeight.W400)
)

val robotoFamily = FontFamily(
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.roboto_medium, weight = FontWeight.Medium),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold)
)