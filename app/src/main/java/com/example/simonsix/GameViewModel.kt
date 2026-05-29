package com.example.simonsix

import android.app.Application
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
    val isShowingSequence: Boolean = false,// indicates whether the sequence is being shown
    val isStartEnabled: Boolean = true,     // indicates whether the Start button can be pressed
    val isPauseEnabled: Boolean = false,    // indicates whether the Pause button can be pressed
    val isFinishEnabled: Boolean = false,   // indicates whether the Finish button can be pressed
    val isGridEnabled: Boolean = false,     // indicates whether the color buttons grid can be pressed
    val activeButton: String? = null,       // the button that change the color
    val isGameOver: Boolean = false,        // indicates if it is game over
    val isPause: Boolean = false,           // indicates whether it is paused
    val resumeIndex: Int = 0                // indicates from which index the sequence should restart when it is paused
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    // expose screen UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    val randomSequence = mutableListOf<String>()

    var isFirstSequence = true

    private var isAlreadySaved = false

    private var currentJob : Job ?= null

    private val repository: GameRepository
    val previousGames: LiveData<List<Game>>

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(2)
        .build()

    private val soundMap = mutableMapOf<String, Int>()

    init {
        // upload the music
        soundMap["R"] = soundPool.load(application, R.raw.sound_r, 1)
        soundMap["Y"] = soundPool.load(application, R.raw.sound_y, 1)
        soundMap["G"] = soundPool.load(application, R.raw.sound_g, 1)
        soundMap["C"] = soundPool.load(application, R.raw.sound_c, 1)
        soundMap["B"] = soundPool.load(application, R.raw.sound_b, 1)
        soundMap["M"] = soundPool.load(application, R.raw.sound_m, 1)

        val gamesDao = GameRoomDatabase.getDatabase(application, viewModelScope).gameDao()
        repository = GameRepository(gamesDao)
        previousGames = repository.allGames
    }

    fun insert(game: Game) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(game)
    }

    fun getGameById(id: Int): LiveData<Game> = repository.getGameById(id)

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

        if (!isFirstSequence && !isAlreadySaved){
            val strRandomSequence = randomSequence.joinToString(",").replace(",","")

            val errorIndex =
                // se si sta mostrando la sequenza e finisce la partita è sbagliata tutta la sequenza
                if(_uiState.value.isShowingSequence)
                    0
                else if (_uiState.value.isGameOver )
                    _uiState.value.sequence.count { it != ',' } - 1
                else {
                    _uiState.value.sequence.count { it != ',' }
                }

            val newGame = Game(0, strRandomSequence, errorIndex)
            insert(newGame)

            isAlreadySaved = true
        }
    }

    fun onColorClicked(letter: String){

        playSound(letter)

        val currentIndex = _uiState.value.sequence.count { it != ',' }

        // Indice fuori bounds = la sequenza è già completa, ignora il tap
        if (currentIndex >= randomSequence.size) return

        _uiState.update { currentState ->
            currentState.copy(
                sequence = _uiState.value.sequence + if (_uiState.value.sequence.isEmpty()) letter else ",$letter",

                // check if the last letter of the sequence is different from the button letter
                isGameOver = randomSequence[_uiState.value.sequence.count { it != ',' }] != letter
            )}

        // update the state if is game over
        if(_uiState.value.isGameOver) {
            onFinishClicked()

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

    override fun onCleared() {
        super.onCleared()
        soundPool.release()
    }

    private fun generateSequence() {
        val list = listOf("R", "Y", "G", "C", "B", "M")

        randomSequence.add(list.random())

        Log.d("StartGame", "Sequenza generata: ${randomSequence}")

    }
    private fun playSound(letter: String) {
        soundMap[letter]?.let { soundId ->
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        }
    }
    private fun playSequence(){
        currentJob?.cancel()

        currentJob = viewModelScope.launch{
            //--- stato iniziale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    sequence = "",
                    isGridEnabled = false,
                    isPauseEnabled = true,
                    isShowingSequence = true
                )
            }

            //--- pulsanti illuminati ---

            delay(300L)

            for (i in _uiState.value.resumeIndex until randomSequence.size){
                _uiState.update { currentState ->
                    currentState.copy(
                        activeButton = randomSequence[i],
                        resumeIndex = i,
                    )
                }
                playSound(randomSequence[i])
                delay(700L)

                _uiState.update { currentState -> currentState.copy(activeButton = null) }
                delay(400L)
            }

            //--- stato finale dei pulsanti ---
            _uiState.update { currentState ->
                currentState.copy(
                    isGridEnabled = true,
                    isPauseEnabled = false,
                    resumeIndex = 0,
                    isShowingSequence = false
                )
            }

            isFirstSequence = false
        }
    }

    fun resetGame(){
        randomSequence.clear()
        isFirstSequence = true
        isAlreadySaved = false

        // reset the state
        _uiState.update { currentState ->
            currentState.copy(
                sequence = "",
                isShowingSequence = false,
                isStartEnabled = true,
                isPauseEnabled = false,
                isFinishEnabled = false,
                isGridEnabled = false,
                activeButton = null,
                isGameOver = false,
                isPause = false,
                resumeIndex = 0
            )
        }
    }
}