package com.example.simonsix

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simonsix.ui.theme.TextFont
import com.example.simonsix.ui.theme.TitleFont

@Composable
fun ChronologyScreen(previousGames: List<Game> ,onPlay: () -> Unit, onGameClicked: (Game) -> Unit) {
    Scaffold(
        floatingActionButton = {FloatingActionButton(
            shape = CircleShape,
            containerColor = colorResource(id = R.color.magenta),
            contentColor = Color.White,
            onClick = onPlay
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null
            )
        } }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //--- TITLE ---
            Text(
                fontFamily = TitleFont,
                fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.9f)
                    .safeDrawingPadding()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //---EMPTY STATE---
                if (previousGames.isEmpty()) {
                    item {
                        Card(modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(30.dp).fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.SportsEsports,
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp),
                                    tint = Color.LightGray
                                )

                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = stringResource(R.string.no_games_yet),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = stringResource(R.string.click_play),
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    }
                } else {
                    items(previousGames) { game ->
                    //itemsIndexed(items = previousGames) { index, game ->
                        Row(
                            Modifier.fillMaxWidth()
                                .clickable(onClick = {onGameClicked(game)}),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.width(48.dp).padding(start=8.dp),
                                text = game.sequence.count { it != '[' && it != ','  && it != ' ' && it != ']' }.toString(),
                                fontSize = 20.sp
                            )

                            ColorTextItem(
                                Modifier
                                    .fillMaxWidth()
                                    .safeDrawingPadding()
                                    .padding(10.dp),
                                game
                            )
                        }
                    }
                }
            }
        }
    }
}

// Assign each row a color by cycling through the six colors in the game:
// red → yellow → green → cyan → blue → magenta → red → ..
@Composable
private fun chooseColor(index: Int): Color
{
    val colors = listOf(
        colorResource(id = R.color.red),
        colorResource(id = R.color.yellow),
        colorResource(id = R.color.green),
        colorResource(id = R.color.cyan),
        colorResource(id = R.color.blue),
        colorResource(id = R.color.magenta)
    )

    return colors[index % colors.size]
}

@Composable
fun ColorTextItem(modifier: Modifier, game: Game) {
    Text(
        modifier = modifier,
        fontSize = 50.sp,
        fontFamily = TextFont,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
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