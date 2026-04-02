package com.example.simonsix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen()
{
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.red)),
                onClick = {}
            ){}
            
            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.magenta)),
                onClick = {}
            ){}
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.green)),
                onClick = {}
            ){}

            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.yellow)),
                onClick = {}
            ){}
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.cyan)),
                onClick = {}
            ){}

            Button(
                modifier = Modifier.height(150.dp).width(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(colorResource(id=R.color.blue)),
                onClick = {}
            ){}
        }
    }
}