package com.example.simonsix

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(onFinishClicked: () -> Unit)
{
    val orientation = LocalConfiguration.current.orientation
    var sequence by rememberSaveable { mutableStateOf("") }

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
                    colors = buttonColors(colorResource(id=R.color.red)),
                    onClick = { sequence += if (sequence == "") "R" else ",R" }
                ){}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(id=R.color.magenta)),
                    onClick = { sequence += if (sequence == "") "M" else ",M" }
                ){}
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(id=R.color.green)),
                    onClick = { sequence += if (sequence == "") "G" else ",G" }
                ){}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(id=R.color.yellow)),
                    onClick = { sequence += if (sequence == "") "Y" else ",Y" }
                ){}
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(id=R.color.cyan)),
                    onClick = { sequence += if (sequence == "") "C" else ",C" }
                ){}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(id=R.color.blue)),
                    onClick = { sequence += if (sequence == "") "B" else ",B" }
                ){}
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(ScrollState(LocalWindowInfo.current.containerSize.height)),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    onClick = { sequence = "" }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear"
                    )

                    Text(
                        modifier = Modifier.padding(8.dp),
                        fontSize = 20.sp,
                        text = stringResource(R.string.cancel)
                    )
                }

                Button(
                    colors = buttonColors(
                        containerColor = colorResource(id = R.color.blue),
                        contentColor = Color.White
                    ),
                    onClick = onFinishClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "finish"
                    )

                    Text(
                        modifier = Modifier.padding(10.dp),
                        fontSize = 20.sp,
                        text = stringResource(R.string.finish)
                    )
                }
            }
        }
    }
    else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.red)),
                    onClick = { sequence += if (sequence == "") "R" else ",R" }
                ) {}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.green)),
                    onClick = { sequence += if (sequence == "") "G" else ",G" }
                ) {}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.cyan)),
                    onClick = { sequence += if (sequence == "") "C" else ",C" }
                ) {}
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.magenta)),
                    onClick = { sequence += if (sequence == "") "M" else ",M" }
                ) {}

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.yellow)),
                    onClick = { sequence += if (sequence == "") "Y" else ",Y" }
                ) {}

                Button(
                    modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = buttonColors(colorResource(R.color.blue)),
                    onClick = { sequence += if (sequence == "") "B" else ",B" }
                ) {}
            }

            Column (modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(10.dp)
                        .verticalScroll(ScrollState(LocalWindowInfo.current.containerSize.height)),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { sequence = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "clear"
                        )

                        Text(
                            modifier = Modifier.padding(8.dp),
                            fontSize = 20.sp,
                            text = stringResource(R.string.cancel)
                        )
                    }

                    Button(
                        colors = buttonColors(
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        ),
                        onClick = onFinishClicked
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "finish"
                        )

                        Text(
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp,
                            text = stringResource(R.string.finish)
                        )
                    }
                }
            }
        }
    }
}
