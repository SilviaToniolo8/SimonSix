package com.example.simonsix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChronologyScreen(prevGames: MutableList<String>, onBackClicked:() -> Unit)
{
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            text = stringResource(R.string.previous_games) + " ${printGames(prevGames)}"
        )

        Button(onClick = onBackClicked){
            Text(text = stringResource(R.string.back))
        }
    }
}

fun printGames(prevGames: MutableList<String>): String
{
    var seq=""
    for(ele in prevGames)
        seq += "$ele \n"
    return seq
}

