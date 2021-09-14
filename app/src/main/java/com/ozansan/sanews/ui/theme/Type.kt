package com.ozansan.sanews.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ozansan.sanews.R

val AzeretMono = FontFamily(
        Font(R.font.azeretmono_regular, FontWeight.Normal),
        Font(R.font.azeretmono_medium, FontWeight.Medium),
        Font(R.font.azeretmono_light, FontWeight.Light)
)

val Abel = FontFamily(
        Font(R.font.abel_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        h5 = TextStyle(
                fontFamily = AzeretMono,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        subtitle1 = TextStyle(
                fontFamily = Abel,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        )
        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)