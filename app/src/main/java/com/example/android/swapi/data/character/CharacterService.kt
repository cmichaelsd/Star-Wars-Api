package com.example.android.swapi.data.character

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
interface CharacterService {
    @GET("/api/people/{character_id}")
    suspend fun getCharacterData(@Path("character_id") characterId: Int): Response<Character>
}