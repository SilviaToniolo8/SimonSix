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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailsGameScreen (game: String)
{
    // scrollState is used to remember the current scroll position
    // https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers
    val scrollState = rememberScrollState()

    // Each time the sequence grows, the scroll automatically scrolls to the bottom. So the last color added is always visible.
    //https://developer.android.com/reference/kotlin/androidx/compose/runtime/LaunchedEffect.composable
    LaunchedEffect(game) { scrollState.animateScrollTo(scrollState.maxValue) }

    //TODO cosa mostrare se uno fa 0

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.game),
            fontSize = 35.sp
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

                Text(text= game.count { it != ',' }.toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Card(
                modifier = Modifier.weight(1f)
            ){
                Text(text = stringResource(R.string.error_button),
                    modifier = Modifier.padding(8.dp)
                )

                //TODO da implementare l'errore
                Text(text= "3",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        ColorText(
            Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .padding(10.dp)
                .verticalScroll(scrollState),
            game,
            false
        )
    }
}
