package com.example.android.swapi.data.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 11/04/20
 *
 */
@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): List<Character>

    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun getById(characterId: Int): Character

    @Query("UPDATE characters SET favorite = NOT favorite WHERE characterId = :characterId")
    fun updateFavorite(characterId: Int)

    @Insert
    suspend fun insertCharacter(character: Character)

    @Insert
    suspend fun insertCharacters(characters: List<Character>)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}