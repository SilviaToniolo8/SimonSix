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

        val strGameId = stringResource(R.string.game) + " ${game.id}"
        val colorStrGameID = buildAnnotatedString {
            var colorIndex = 0

            // every character of the title, expect the space, has a different color in order
            // (red-yellow-green-cyan-blue-magenta)
            strGameId.forEach { char ->
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

        //--- TITLE ---
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 60.sp,
            fontFamily = GameFont,
            text = colorStrGameID
        )

        //--- NUMBER OF BUTTON PRESSED ---
        Column(Modifier.safeDrawingPadding()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.button_pressed),
                        modifier = Modifier.padding(8.dp)
                    )

                    Text(
                        text = if (game.errorIndex == game.sequence.length)
                            game.sequence.length.toString()
                        else
                            (game.sequence.length-1).toString(),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            //--- SEQUENCE OF BUTTON ---
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding()
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(scrollState),
                fontSize = 50.sp,
                fontFamily = TextFont,
                fontWeight = FontWeight.Bold,
                lineHeight = 55.sp,
                letterSpacing = 3.sp,
                text = buildAnnotatedString {
                    for ((index, c) in game.sequence.withIndex()) {
                        if (index >= game.errorIndex && index != game.sequence.length) {
                            withStyle(style = SpanStyle(Color(colorResource(id = R.color.gray).toArgb()))) {
                                append(
                                    c.lowercase()
                                )
                            }
                        } else
                            withStyle(SpanStyle(simonCharColor(c))) { append(c.lowercase()) }
                    }
                }
            )
        }
    }
}