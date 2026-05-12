package com.example.simonsix

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
                        startDestination = "chronology",
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        // Chronology screen: shows all sequences of previous matches
                        composable(route = "chronology") {
                            ChronologyScreen(
                                onPlay = { navController.navigate("game") },
                                onGameClicked = { g ->
                                    navController.navigate("details/${Uri.encode(g)}") {
                                        popUpTo(route = "details/${Uri.encode(g)}")
                                    }
                                }
                            )
                        }

                        composable(route = "details/{game}") { backStackEntry ->
                            DetailsGameScreen(
                                Uri.decode(
                                    backStackEntry.arguments?.getString("game").orEmpty()
                                )
                            )
                        }

                        composable(route = "game") {
                            val viewModel: GameViewModel = viewModel()
                            val state by viewModel.uiState.collectAsStateWithLifecycle()

                            GameScreen(
                                state,
                                onStartClicked = viewModel::onStartClicked,
                                onColorClicked = viewModel::onColorClicked,
                                onFinishClicked = { seq ->
                                    // The new sequence is inserted at the top of the list so the most recent one always appears first
                                    viewModel.previousGames.add(0, seq)
                                    navController.navigate("chronology"){
                                        popUpTo(route = "chronology")}
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}