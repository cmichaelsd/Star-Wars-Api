package com.example.android.swapi.utilities

import android.content.Context

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
class FileHelper {
    companion object {
        fun getTextFromResources(context: Context, resourceId: Int): String {
            return context.resources.openRawResource(resourceId).use { inputStream ->
                inputStream.bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}