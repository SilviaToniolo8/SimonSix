package com.example.simonsix

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "sequence") val sequence: String,
    @ColumnInfo(name = "errorIndex") val errorIndex: Int
)
