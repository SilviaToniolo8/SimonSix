package com.example.simonsix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.ScrollState
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
    var sequence by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.red)),
                onClick = { sequence += "R" }
            ){}

            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.magenta)),
                onClick = { sequence += "M" }
            ){}
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.green)),
                onClick = { sequence += "G" }
            ){}

            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.yellow)),
                onClick = { sequence += "Y" }
            ){}
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.cyan)),
                onClick = { sequence += "C" }
            ){}

            Button(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.blue)),
                onClick = { sequence += "B" }
            ){}
        }

        Text(
            modifier = Modifier
                .height(160.dp)
                .padding(16.dp)
                .verticalScroll(ScrollState(LocalWindowInfo.current.containerSize.height)),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 5.sp,
            lineHeight = 30.sp,
            text = buildAnnotatedString{
                for(c in sequence){
                    when(c){
                        'R' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.red).toArgb()))) { append("R") }
                        'Y' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.yellow).toArgb()))) { append("Y") }
                        'G' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.green).toArgb()))) { append("G") }
                        'C' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.cyan).toArgb()))) { append("C") }
                        'B' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.blue).toArgb()))) { append("B") }
                        'M' -> withStyle(style = SpanStyle(Color(colorResource(id = R.color.magenta).toArgb()))) { append("M") }
                    }
                }
            }
        )

        Row(
            modifier = Modifier.width(300.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            TextButton(
                modifier = Modifier.height(70.dp),
                onClick = { sequence = "" }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "clear")

                Text(fontSize = 18.sp,
                    text = stringResource(R.string.cancel))
            }

            Button(
                modifier = Modifier.height(70.dp),
                colors = buttonColors(colorResource(id=R.color.blue), contentColor = Color.White),
                onClick = onFinishClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "finish")

                Text(
                    fontSize = 17.sp,
                    text = stringResource(R.string.finish))
            }
        }

    }
}
