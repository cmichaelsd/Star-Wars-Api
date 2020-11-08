package com.example.android.swapi.data.swapi

import com.example.android.swapi.data.character.Character

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
data class SwapiResponse (
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Character>
)