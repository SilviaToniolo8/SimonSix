package com.example.simonsix

import kotlinx.coroutines.delay
import android.util.Log
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simonsix.ui.theme.GameOverFont
import com.example.simonsix.ui.theme.TextFont
import com.example.simonsix.ui.theme.TitleFont

@Composable
fun GameScreen(game: GameUiState, onStartClicked: () -> Unit, onColorClicked: (String) -> Unit, onPauseClicked: () -> Unit, onFinishClicked: () -> Unit)
{
    val orientation = LocalConfiguration.current.orientation

    // scrollState is used to remember the current scroll position
    // https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers
    val scrollState = rememberScrollState()

    // Each time the sequence grows, the scroll automatically scrolls to the bottom. So the last color added is always visible.
    //https://developer.android.com/reference/kotlin/androidx/compose/runtime/LaunchedEffect.composable
    LaunchedEffect(game.sequence) { scrollState.animateScrollTo(scrollState.maxValue) }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                fontFamily = TitleFont,
                fontWeight = FontWeight.Bold,
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

            Box(modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center) {
                GridSixButtonsLayout(
                    modifier = Modifier
                        .fillMaxWidth(),
                    activeButton = game.activeButton,
                    isClickable = game.isGridEnabled,
                    onColorClicked = onColorClicked
                )

                if(game.isGameOver){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                            .background(
                                Color.DarkGray.copy(0.5f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        text = stringResource(R.string.game_over),
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_red),
                        fontFamily = GameOverFont,
                        textAlign = TextAlign.Center,
                        lineHeight = 80.sp,
                        letterSpacing = 10.sp
                    )
                }}

            ColorText(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(scrollState),
                game.sequence,
                game.isGameOver
            )

            ActionButtons(
                game.isStartEnabled,
                game.isPauseEnabled,
                game.isPause,
                game.isFinishEnabled,
                onStartClicked = onStartClicked,
                onPauseClicked = onPauseClicked,
                onFinishClicked = onFinishClicked
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontFamily = TitleFont,
                    fontWeight = FontWeight.Bold,
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

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 50.dp, bottom = 25.dp, top = 25.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GridSixButtonsLayout(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        activeButton = game.activeButton,
                        isClickable = game.isGridEnabled,
                        onColorClicked = onColorClicked
                    )
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
                            game.sequence,
                            game.isGameOver
                        )

                        ActionButtons(
                            game.isStartEnabled,
                            game.isPauseEnabled,
                            game.isPause,
                            game.isFinishEnabled,
                            onStartClicked = onStartClicked,
                            onPauseClicked = onPauseClicked,
                            onFinishClicked = onFinishClicked
                        )
                    }
                }
            }

            if(game.isGameOver){
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(32.dp)
                        .background(Color.DarkGray.copy(0.5f), shape = RoundedCornerShape(12.dp)),
                    text = stringResource(R.string.game_over),
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_red),
                    fontFamily = GameOverFont,
                    textAlign = TextAlign.Center,
                    lineHeight = 80.sp,
                    letterSpacing = 10.sp
                )
            }
        }
    }
}

// Displays the six button in a grid 2x3.
@Composable
private fun GridSixButtonsLayout(modifier: Modifier, activeButton: String?, isClickable: Boolean, onColorClicked:(String)->Unit)
{
    var pressedButton by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(pressedButton) {
        if (pressedButton != null) {
            delay(300L)
            pressedButton = null
        }
    }

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
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "R" -> Color.Red
                        else -> colorResource(id = R.color.red)
                    },
                    disabledContainerColor = when{
                        activeButton == "R" -> Color.Red
                        else -> colorResource(id = R.color.red).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "R"
                    onColorClicked("R")
                }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "M" -> Color.Magenta
                        else -> colorResource(id = R.color.magenta)
                    },
                    disabledContainerColor = when{
                        activeButton == "M" -> Color.Magenta
                        else -> colorResource(id = R.color.magenta).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "M"
                    onColorClicked("M")
                }
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
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "G" -> Color.Green
                        else -> colorResource(id = R.color.green)
                    },
                    disabledContainerColor = when{
                        activeButton == "G" -> Color.Green
                        else -> colorResource(id = R.color.green).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "G"
                    onColorClicked("G")
                }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "Y" -> Color.Yellow
                        else -> colorResource(id = R.color.yellow)
                    },
                    disabledContainerColor = when{
                        activeButton == "Y" -> Color.Yellow
                        else -> colorResource(id = R.color.yellow).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "Y"
                    onColorClicked("Y")
                }
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
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "C" -> Color.Cyan
                        else -> colorResource(id = R.color.cyan)
                    },
                    disabledContainerColor = when{
                        activeButton == "C" -> Color.Cyan
                        else -> colorResource(id = R.color.cyan).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "C"
                    onColorClicked("C")
                }
            ){}

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = isClickable,
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = when{
                        pressedButton == "B" -> Color.Blue
                        else -> colorResource(id = R.color.blue)
                    },
                    disabledContainerColor = when{
                        activeButton == "B" -> Color.Blue
                        else -> colorResource(id = R.color.blue).copy(alpha = 0.65f)
                    }
                ),
                onClick = {
                    pressedButton = "B"
                    onColorClicked("B")
                }
            ) {}
        }
    }
}

// Displays the sequence with each letter in the color of the corresponding button.
// buildAnnotatedString allows different styles for each character in the same Text.
// The Color is constructed by ARGB because SpanStyle doesn't directly accept a Compose Color.
//https://developer.android.com/develop/ui/compose/text/style-text#multiple-styles
@Composable
fun ColorText(modifier: Modifier, sequence: String, isGameOver: Boolean) {
    Text(
        modifier = modifier,
        fontSize = 50.sp,
        fontFamily = TextFont,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        text = buildAnnotatedString {
            for ((index, c) in sequence.withIndex()) {
                if (isGameOver && index == sequence.length-1){
                    withStyle(style = SpanStyle(Color(colorResource(id = R.color.gray).toArgb()))) {append(c.lowercase())}
                }
                else when (c) {
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
private fun ActionButtons(isStart: Boolean, isPause: Boolean, isPauseClicked: Boolean, isFinish: Boolean, onStartClicked: () -> Unit, onPauseClicked: () -> Unit, onFinishClicked:() -> Unit){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //START GAME
        Button(
            modifier = Modifier.size(70.dp),
            enabled = isStart,
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(
                colorResource(id = R.color.red),
                Color.White),
            onClick = onStartClicked
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
            enabled = isPause,
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(Color.LightGray),
            onClick = onPauseClicked
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if(isPauseClicked) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                    contentDescription = null
                )

                Text(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    text =  if(isPauseClicked) stringResource(R.string.resume) else stringResource(R.string.pause),

                )
            }
        }

        //FINISH
        Button(
            modifier = Modifier.size(70.dp),
            enabled = isFinish,
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = buttonColors(
                colorResource(id = R.color.emerald),
                Color.White
            ),
            //TODO implementare fine partita
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