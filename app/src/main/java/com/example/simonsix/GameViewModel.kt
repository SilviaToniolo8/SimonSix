package com.example.simonsix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.text.count
import kotlin.text.isEmpty

// data for the ui
data class GameUiState (
    val sequence: String = "",              // sequence of buttons clicked by the player
    val isStartEnabled: Boolean = true,     // indicates whether the Start button can be pressed
    val isPauseEnabled: Boolean = false,    // indicates whether the Pause button can be pressed
    val isFinishEnabled: Boolean = false,   // indicates whether the Finish button can be pressed
    val isGridEnabled: Boolean = false,     // indicates whether the color buttons grid can be pressed
    val activeButton: String? = null,        // the button that change the color
    val isGameOver: Boolean = false,         // indicates if it is game over
    val isPause: Boolean = false,            // indicates whether it is paused
    val resumeIndex: Int = 0               // indicates from which index the sequence should restart when it is paused
)

class GameViewModel : ViewModel() {
    // expose screen UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val randomSequence = mutableListOf<String>()

    var isFirstSequence = true

    // previousGames is the list of sequences already played, shared between screens
    val previousGames = mutableListOf<String>()

   private var currentJob : Job ?= null

    fun onStartClicked(){
        _uiState.update { currentState ->
            currentState.copy(
                isStartEnabled = false,
                isFinishEnabled = true
            )
        }

        generateSequence()

        playSequence()
    }

    fun onPauseClicked(){
        _uiState.update { currentState -> currentState.copy(isPause = !_uiState.value.isPause) }

        // pause
        if(_uiState.value.isPause){
            _uiState.update { currentState -> currentState.copy(resumeIndex = _uiState.value.resumeIndex + 1)}
            currentJob?.cancel()

        } else {    //resume
            playSequence()
        }
    }

    fun onFinishClicked(){
        currentJob?.cancel()

        if (!isFirstSequence)
            GamesData.previousGames.add(0, randomSequence.toString())
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
                    activeButton = null,
                    isPauseEnabled = false,
                    isPause = false,
                    isStartEnabled = false,
                    isFinishEnabled = false
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

    private fun playSequence(){
        currentJob?.cancel()

        currentJob = viewModelScope.launch{
            //--- stato iniziale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    sequence = "",
                    isGridEnabled = false,
                    isPauseEnabled = true
                )
            }

            //--- pulsanti illuminati ---

            delay(300L)

            for (i in _uiState.value.resumeIndex until randomSequence.size){
                _uiState.update { currentState ->
                    currentState.copy(
                        activeButton = randomSequence[i],
                        resumeIndex = i
                    )
                }
                delay(700L)

                _uiState.update { currentState -> currentState.copy(activeButton = null) }
                delay(400L)
            }

            //--- stato finale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    isGridEnabled = true,
                    isPauseEnabled = false,
                    resumeIndex = 0
                )
            }

            isFirstSequence = false
        }
    }
}
