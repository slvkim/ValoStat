package com.mikyegresl.valostat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColors()

private val DarkColors = darkColors(
    primary = mainBackgroundDark,
    onPrimary = mainTextDark,
    secondary = secondaryBackgroundDark,
    onSecondary = secondaryTextDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    error = errorDark,
    onError = onErrorDark,
//    primaryVariant = mainBackgroundDark
)

@Composable
fun ValoStatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else
//                dynamicLightColorScheme(context)
//        }
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }
    MaterialTheme(
        colors = DarkColors,
        typography = ValoStatTypography,
        content = content
    )
}

val ValoStatTypography = Typography(
    h3 = TextStyle(
        fontFamily = drukWide,
        fontWeight = FontWeight.W500,
        color = mainTextDark,
        fontSize = 30.sp
    ),
    h4 = TextStyle(
        color = Color.Black,
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W600,
        color = mainTextDark,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W500,
        color = mainTextDark,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W400,
        color = mainTextDark,
        fontSize = 13.sp
    ),
    body1 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    ),
    body2 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
    ),
    button = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = mainTextDark
    ),
    overline = TextStyle(
        fontFamily = bowlyOne,
        fontWeight = FontWeight.W700,
        fontSize = 12.sp
    )
)