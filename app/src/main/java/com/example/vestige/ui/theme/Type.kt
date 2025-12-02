package com.example.vestige.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vestige.R

// Set of Material typography styles to start with
val rsmFamily = FontFamily(
    Font(resId = R.font.robotoslab_medium, weight = FontWeight.Medium),
            Font(resId = R.font.robotoslab_light, weight = FontWeight.Light),
            Font(resId = R.font.robotosb_regular, weight = FontWeight.Normal)

)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = rsmFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)