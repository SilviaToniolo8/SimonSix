package com.example.simonsix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
                val viewModel: GameViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "chronology",
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        // Chronology screen: shows all sequences of previous matches
                        composable(route = "chronology") {
                            val games by viewModel.previousGames.observeAsState(emptyList())

                            ChronologyScreen(
                                previousGames = games,
                                onPlay = { navController.navigate("game") },
                                onGameClicked = { game ->
                                    navController.navigate("details/${game.id}")
                                }
                            )
                        }

                        composable(route = "details/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                            val game by viewModel.getGameById(id).observeAsState()

                            game?.let {
                                DetailsGameScreen(it)
                            }
                        }

                        composable(route = "game") {
                            val state by viewModel.uiState.collectAsStateWithLifecycle()

                            GameScreen(
                                state,
                                onStartClicked = viewModel::onStartClicked,
                                onColorClicked = viewModel::onColorClicked,
                                onPauseClicked = viewModel::onPauseClicked,
                                onFinishClicked = {
                                    viewModel.onFinishClicked()

                                    navController.navigate("chronology"){
                                        popUpTo(0) { inclusive = true }}
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}