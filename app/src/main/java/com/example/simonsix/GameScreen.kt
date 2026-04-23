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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                { sequence = "" },
                {
                    val s = sequence
                    sequence = ""
                    onFinishClicked(s)
                })
        }
    }
    else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GridSixButtonsLayout(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()) { letter ->
                sequence += if (sequence.isEmpty()) letter else ",$letter"
            }

            Column (modifier = Modifier
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
                    { sequence = "" },
                    {
                        val s = sequence
                        sequence = ""
                        onFinishClicked(s)
                    })
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
private fun ColorText(modifier: Modifier, sequence: String) {
    Text(
        modifier = modifier,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 5.sp,
        lineHeight = 35.sp,
        text = buildAnnotatedString {
            for (c in sequence) {
                when (c) {
                    'R' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {append("R")}
                    'Y' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {append("Y")}
                    'G' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {append("G")}
                    'C' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) {append("C")}
                    'B' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) {append("B")}
                    'M' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.magenta).toArgb()))) {append("M")}
                    ',' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.white).toArgb()))) {append(", ")}
                }
            }
        }
    )
}

// Displays two buttons in a row:
// Cancel button -> clear the sequence
// Finish button -> finish the sequence and go to other screen
@Composable
private fun ActionButtons(onCancelClick:() -> Unit, onFinishClicked:() -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton( onClick = onCancelClick ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                text = stringResource(R.string.cancel)
            )
        }

        Button(
            onClick = onFinishClicked,
            colors = buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 20.sp,
                text = stringResource(R.string.finish)
            )
        }
    }
}
