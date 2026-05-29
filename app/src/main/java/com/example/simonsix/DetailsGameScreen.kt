package com.example.simonsix

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simonsix.ui.theme.TextFont
import com.example.simonsix.ui.theme.GameFont

@Composable
fun DetailsGameScreen (game: Game)
{
    // scrollState is used to remember the current scroll position
    // https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers
    val scrollState = rememberScrollState()

    // Each time the sequence grows, the scroll automatically scrolls to the bottom. So the last color added is always visible.
    //https://developer.android.com/reference/kotlin/androidx/compose/runtime/LaunchedEffect.composable
    LaunchedEffect(game) { scrollState.animateScrollTo(scrollState.maxValue) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val colors = listOf(
            colorResource(R.color.red),
            colorResource(R.color.yellow),
            colorResource(R.color.green),
            colorResource(R.color.cyan),
            colorResource(R.color.blue),
            colorResource(R.color.magenta)
        )

        val fullText = stringResource(R.string.game) + " ${game.id}"

        val annotatedText = buildAnnotatedString {
            var colorIndex = 0

            fullText.forEach { char ->
                if (char == ' ') {
                    append(' ')
                } else {
                    withStyle(SpanStyle(color = colors[colorIndex % colors.size])) {
                        append(char)
                    }
                    colorIndex++
                }
            }
        }

        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 60.sp,
            fontFamily = GameFont,
            text = annotatedText
        )

        Row(
            Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(end=16.dp)
            ){
                Text(text = stringResource(R.string.button_pressed),
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = game.sequence.count { it != '[' && it != ','  && it != ' ' && it != ']' }.toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        ColorTextDetail(
            Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(scrollState),
            game
        )
    }
}

@Composable
fun ColorTextDetail(modifier: Modifier, game: Game) {
    Text(
        modifier = modifier,
        fontSize = 50.sp,
        fontFamily = TextFont,
        fontWeight = FontWeight.Bold,
        lineHeight = 55.sp,
        letterSpacing = 3.sp,
        text = buildAnnotatedString {
            for ((index, c) in game.sequence.withIndex()) {
                if (index >= game.errorIndex && index != game.sequence.length){
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.gray).toArgb()))) {append(c.lowercase())}
                }
                else when (c) {
                    'R' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {append("r")}
                    'Y' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {append("y")}
                    'G' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {append("g")}
                    'C' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) {append("c")}
                    'B' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) {append("b")}
                    'M' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.magenta).toArgb()))) {append("m")}
                    ',' -> withStyle(style = SpanStyle(color = Color.White)) {append(",")}
                }
            }
        }
    )
}