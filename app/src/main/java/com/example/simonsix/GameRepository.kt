package com.example.simonsix

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class GameRepository(private val gameDao: GameDao) {

    val allGames: LiveData<List<Game>> = gameDao.getAll()

    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }

    fun getGameById(id: Int): LiveData<Game> = gameDao.getGameById(id)
}