package com.example.simonsix

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChronologyScreen()
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 30.sp,
            text = stringResource(R.string.previous_games)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .safeDrawingPadding()
                .padding(8.dp)
        ) {
            itemsIndexed( items = GamesData.previousGames ){ index, game ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.width(48.dp),
                        text = game.count { it != ',' }.toString(),
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 35.sp,
                        letterSpacing = 5.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        text = game,
                        color = chooseColor(index)
                    )
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
