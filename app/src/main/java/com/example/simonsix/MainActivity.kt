package com.example.simonsix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simonsix.ui.theme.SimonSixTheme

class MainActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            SimonSixTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "game",
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        composable (route="game") {
                            GameScreen(
                                onFinishClicked = { seq ->
                                    // The new sequence is inserted at the top of the list so the most recent one always appears first
                                    GamesData.previousGames.add(0,seq)
                                    navController.navigate("chronology")
                                }
                            )
                        }

                        // Chronology screen: shows all sequences of previous matches
                        composable (route="chronology") {
                            ChronologyScreen()
                        }
                    }
                }
            }
        }
    }
}
