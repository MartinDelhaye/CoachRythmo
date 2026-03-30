package com.example.coachrythmo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coachrythmo.R

val Orbitron = FontFamily(
    Font(R.font.orbitron_variable_font_wght, FontWeight.Bold)
)

val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

// Typography Material3
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Orbitron,
        fontSize = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Orbitron,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Orbitron,
        fontSize = 19.sp
    )
)