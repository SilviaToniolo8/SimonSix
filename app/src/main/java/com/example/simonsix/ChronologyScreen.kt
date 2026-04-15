package com.example.simonsix

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.text.iterator

@Composable
fun ChronologyScreen(prevGames: MutableList<String>, onBackClicked:() -> Unit) {
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

        LazyColumn(modifier = Modifier.fillMaxSize().safeDrawingPadding().weight(0.8f).padding(8.dp)) {
            items(prevGames){ game ->
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.weight(0.1f),
                        text = game.filterNot{ it == ',' }.length.toString(),
                        fontSize = 20.sp
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(10.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 5.sp,
                        lineHeight = 35.sp,
                        text = buildAnnotatedString {
                            for (c in game) {
                                when (c) {
                                    'R' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) {append("R")}
                                    'Y' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) {append("Y")}
                                    'G' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) {append("G")}
                                    'C' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) {append("C")}
                                    'B' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) {append("B")}
                                    'M' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.magenta).toArgb()))) {append("M")}
                                    ',' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.white).toArgb()))) {append(",")}
                                }
                            }
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier.height(80.dp).padding(10.dp),
            colors = buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = Color.White
            ),
            onClick = onBackClicked
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )

            Text(
                fontSize = 20.sp,
                text = stringResource(R.string.back)
            )
        }
    }
}

