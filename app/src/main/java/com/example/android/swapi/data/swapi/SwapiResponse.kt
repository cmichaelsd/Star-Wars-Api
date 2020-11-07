package com.example.android.swapi.data.swapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.swapi.data.character.Character

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
@Entity(tableName = "swapi_response")
data class SwapiResponse (
    @PrimaryKey(autoGenerate = true)
    val swapiResponseId: Int,
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Character>
)