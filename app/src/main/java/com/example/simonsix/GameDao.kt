package com.example.simonsix

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    fun getAll(): LiveData<List<Game>>

    @Insert(Game::class)
    fun insert(game: Game)

    @Query("SELECT * FROM game_table WHERE id = :id")
    fun getGameById(id: Int): LiveData<Game>
}