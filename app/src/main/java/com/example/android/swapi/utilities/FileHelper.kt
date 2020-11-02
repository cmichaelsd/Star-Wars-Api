package com.example.android.swapi.utilities

import android.app.Application
import android.content.Context
import java.io.File

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

        fun saveTextToFile(app: Application, json: String?) {
            val file = File(app.filesDir, "swapi.json")
            file.writeText(json ?: "", Charsets.UTF_8)
        }

        fun saveTextToCache(app: Application, json: String?) {
            val file = File(app.cacheDir, "swapi.json")
            file.writeText(json ?: "", Charsets.UTF_8)
        }
    }
}