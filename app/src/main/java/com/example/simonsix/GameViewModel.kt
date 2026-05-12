package com.example.simonsix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.count
import kotlin.text.isEmpty

// data for the ui
data class GameUiState (
    val sequence: String = "",              // sequence of buttons clicked by the player
    val isStartEnabled: Boolean = true,     // indicates whether the Start button can be pressed
    val isShowingSequence: Boolean = false,  // indicates whether the sequence should be shown
    val isPauseEnabled: Boolean = false,    // indicates whether the Pause button can be pressed
    val isFinishEnabled: Boolean = false,   // indicates whether the Finish button can be pressed
    val isGridEnabled: Boolean = false,     // indicates whether the color buttons grid can be pressed
    val activeButton: String? = null,        // the button that change the color
    val isGameOver: Boolean = false         // indicates if it is game over
)

class GameViewModel : ViewModel() {
    // expose screen UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val randomSequence = mutableListOf<String>()

    // previousGames is the list of sequences already played, shared between screens
    val previousGames = mutableListOf<String>()

    fun onStartClicked(){
        viewModelScope.launch{
            //--- stato iniziale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    sequence = "",
                    isStartEnabled = false,
                    isGridEnabled = false,
                    isShowingSequence = true
                )
            }

            //--- pulsanti illuminati ---

            delay(300L)
            generateSequence()

            for (i in 0 until randomSequence.size){
                _uiState.update { currentState -> currentState.copy(activeButton = randomSequence[i]) }
                delay(700L)

                _uiState.update { currentState -> currentState.copy(activeButton = null) }
                delay(400L)
            }

            //--- stato finale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    isShowingSequence = false,
                    isGridEnabled = true
                )
            }
        }
    }

    fun onColorClicked(letter: String){
        _uiState.update { currentState ->
            currentState.copy(
                sequence = _uiState.value.sequence + if (_uiState.value.sequence.isEmpty()) letter else ",$letter",

                // check if the last letter of the sequence is different from the button letter
                isGameOver = randomSequence[_uiState.value.sequence.count { it != ',' }] != letter
            )}

        // update the state if is game over
        if(_uiState.value.isGameOver) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGridEnabled = false,
                    isStartEnabled = false
                )}
        }

        else if(_uiState.value.sequence.count { it != ',' } == randomSequence.size){
            viewModelScope.launch{delay(500L)
                onStartClicked()}}
    }
    private fun generateSequence() {
        val list = listOf("R", "Y", "G", "C", "B", "M")

        randomSequence.add(list.random())

        Log.d("StartGame", "Sequenza generata: ${randomSequence}")

    }
}
