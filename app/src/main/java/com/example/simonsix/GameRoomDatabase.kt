package com.example.simonsix

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Game::class], version = 1)
abstract class GameRoomDatabase : RoomDatabase() {
    abstract fun gameDao() : GameDao

    companion object{ 
        private var INSTANCE: GameRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): GameRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameRoomDatabase::class.java,
                    "game_database"
                )
                    .build()
                 
                INSTANCE = instance
                instance
            }
        }
    }
}