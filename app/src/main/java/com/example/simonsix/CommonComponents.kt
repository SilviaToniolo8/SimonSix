package com.example.simonsix

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.simonsix.ui.theme.TitleFont

//Function that displays the multicolored SimonSix title
@Composable
fun SimonSixTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        fontFamily = TitleFont,
        fontWeight = FontWeight.Bold,
        fontSize = 55.sp,
        letterSpacing = 3.sp,
        text = buildAnnotatedString {
            withStyle(SpanStyle(Color(colorResource(R.color.red).toArgb()))) { append("S") }
            withStyle(SpanStyle(Color(colorResource(R.color.yellow).toArgb()))) { append("i") }
            withStyle(SpanStyle(Color(colorResource(R.color.green).toArgb()))) { append("m") }
            withStyle(SpanStyle(Color(colorResource(R.color.cyan).toArgb()))) { append("o") }
            withStyle(SpanStyle(Color(colorResource(R.color.blue).toArgb()))) { append("n ") }
            withStyle(SpanStyle(Color(colorResource(R.color.red).toArgb()))) { append("S") }
            withStyle(SpanStyle(Color(colorResource(R.color.yellow).toArgb()))) { append("i") }
            withStyle(SpanStyle(Color(colorResource(R.color.green).toArgb()))) { append("x") }
        }
    )
}

//Function that associates a color with a given character
@Composable
fun simonCharColor(c: Char): Color = when (c) {
    'R' -> Color(colorResource(R.color.red).toArgb())
    'Y' -> Color(colorResource(R.color.yellow).toArgb())
    'G' -> Color(colorResource(R.color.green).toArgb())
    'C' -> Color(colorResource(R.color.cyan).toArgb())
    'B' -> Color(colorResource(R.color.blue).toArgb())
    'M' -> Color(colorResource(R.color.magenta).toArgb())
    else -> Color.White
}