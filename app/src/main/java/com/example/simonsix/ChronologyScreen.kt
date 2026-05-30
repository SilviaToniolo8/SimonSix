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

@Composable
fun ChronologyScreen(previousGames: List<Game> ,onPlay: () -> Unit, onGameClicked: (Game) -> Unit) {
    Scaffold(
        //--- START GAME ---
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
            SimonSixTitle()

            //--- GAMES LIST ---
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

                    //--- GAME ITEM ---
                    items(previousGames) { game ->

                        Row(
                            Modifier.fillMaxWidth()
                                .clickable(onClick = {onGameClicked(game)}),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            //--- NUMBER OF BUTTON PRESSED ---
                            Text(
                                modifier = Modifier.width(48.dp).padding(start = 10.dp),
                                text = game.sequence.length.toString(),
                                fontSize = 20.sp
                            )

                            //--- SEQUENCE OF BUTTON ---
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .safeDrawingPadding()
                                    .padding(10.dp),
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
                                        else
                                             withStyle(SpanStyle(simonCharColor(c))) { append(c.lowercase()) }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}