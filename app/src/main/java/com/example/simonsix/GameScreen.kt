package com.example.simonsix

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simonsix.ui.theme.TextFont
import com.example.simonsix.ui.theme.TitleFont

@Composable
fun GameScreen(onFinishClicked: (String) -> Unit)
{
    val orientation = LocalConfiguration.current.orientation

    var sequence by rememberSaveable { mutableStateOf("") }

    // scrollState is used to remember the current scroll position
    // https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers
    val scrollState = rememberScrollState()

    // Each time the sequence grows, the scroll automatically scrolls to the bottom. So the last color added is always visible.
    //https://developer.android.com/reference/kotlin/androidx/compose/runtime/LaunchedEffect.composable
    LaunchedEffect(sequence) { scrollState.animateScrollTo(scrollState.maxValue) }

    if (orientation == Configuration.ORIENTATION_PORTRAIT)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                fontFamily = TitleFont,
                fontSize = 55.sp,
                letterSpacing = 3.sp,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) { append("S") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) { append("i") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) { append("m") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) { append("o") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) { append("n ") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) { append("S") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) { append("i") }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) { append("x") }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            GridSixButtonsLayout(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()) { letter ->
                    sequence += if (sequence.isEmpty()) letter else ",$letter"
            }

            ColorText(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(scrollState),
                sequence
            )

            ActionButtons(
                {
                    val s = sequence
                    sequence = ""
                    onFinishClicked(s)
                })
        }
    }
    else {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontFamily = TitleFont,
                fontSize = 55.sp,
                letterSpacing = 3.sp,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {
                        append(
                            "S"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {
                        append(
                            "i"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {
                        append(
                            "m"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) {
                        append(
                            "o"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) {
                        append(
                            "n "
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {
                        append(
                            "S"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {
                        append(
                            "i"
                        )
                    }
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {
                        append(
                            "x"
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 50.dp, bottom = 25.dp, top=25.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GridSixButtonsLayout(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                ) { letter ->
                    sequence += if (sequence.isEmpty()) letter else ",$letter"
                }

                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                ) {

                    ColorText(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .verticalScroll(scrollState),
                        sequence
                    )

                    ActionButtons(
                        {
                            val s = sequence
                            sequence = ""
                            onFinishClicked(s)
                        }
                    )
                }
            }
        }
    }
}

// Displays the six button in a grid 2x3.
@Composable
private fun GridSixButtonsLayout(modifier: Modifier, onColorClicked:(String)->Unit)
{
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.red)),
                onClick = { onColorClicked("R") }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.magenta)),
                onClick = { onColorClicked("M") }
            ){}
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.green)),
                onClick = { onColorClicked("G") }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.yellow)),
                onClick = { onColorClicked("Y") }
            ){}
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.cyan)),
                onClick = { onColorClicked("C") }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id = R.color.blue)),
                onClick = { onColorClicked("B") }
            ) {}
        }
    }
}

// Displays the sequence with each letter in the color of the corresponding button.
// buildAnnotatedString allows different styles for each character in the same Text.
// The Color is constructed by ARGB because SpanStyle doesn't directly accept a Compose Color.
//https://developer.android.com/develop/ui/compose/text/style-text#multiple-styles
@Composable
public fun ColorText(modifier: Modifier, sequence: String) {
    Text(
        modifier = modifier,
        fontSize = 50.sp,
        fontFamily = TextFont,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        text = buildAnnotatedString {
            for (c in sequence) {
                when (c) {
                    'R' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {append("r")}
                    'Y' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {append("y")}
                    'G' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {append("g")}
                    'C' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) {append("c")}
                    'B' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) {append("b")}
                    'M' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.magenta).toArgb()))) {append("m")}
                    ',' -> withStyle(style = SpanStyle(color = Color.White)) {append(", ")}
                }
            }
        }
    )
}

// Displays tree buttons in a row:
// Start button -> start the sequence
// Pause button -> stop the game
// Finish button -> finish the sequence and go to other screen
@Composable
private fun ActionButtons(onFinishClicked:() -> Unit){
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //START GAME
        Button(
            modifier = Modifier.size(70.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(
                colorResource(id = R.color.red),
                Color.White),
            //TODO implementare avvia partita
            onClick = { }
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null
                )

                Text(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(R.string.start)
                )
            }
        }

        //PAUSA
        Button(
            modifier = Modifier.size(70.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(Color.LightGray),
            //TODO implementare stop partita
            onClick = { }
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Pause,
                contentDescription = null
            )

            Text(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.pause)
            )
            }
        }

        //FINISH
        Button(
            modifier = Modifier.size(70.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(
                colorResource(id = R.color.emerald),
                Color.White
            ),
            onClick = onFinishClicked
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null
                )

                Text(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.end)
                )
            }
        }
    }
}