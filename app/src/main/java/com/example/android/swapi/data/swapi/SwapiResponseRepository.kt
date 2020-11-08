package com.example.android.swapi.data.swapi

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.R
import com.example.android.swapi.WEB_SERVICE_URL
import com.example.android.swapi.data.character.Character
import com.example.android.swapi.data.network.NetworkOperationsImpl
import com.example.android.swapi.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
class SwapiResponseRepository(val app: Application) : NetworkOperationsImpl() {

    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val service: SwapiResponseService

    private val adapter: JsonAdapter<SwapiResponse> = moshi.adapter(SwapiResponse::class.java)

    private val characterType: Type = Types.newParameterizedType(List::class.java, Character::class.java)
    private val characterAdapter: JsonAdapter<List<Character>> = moshi.adapter(characterType)

    private val pageNumber = 1

    val swapiResponsesData = MutableLiveData<List<Character>>()

    init {
        service = createService()

        val data = readDataFromExternalFiles()
        if (data.count() == 0) {
            refreshDataFromWeb()
        } else {
            swapiResponsesData.value = data
            Log.i(LOG_TAG, "Using local data")
        }
    }

    // tag this as a worker thread
    // this logic will be ran on a background thread
    @WorkerThread
    private suspend fun callWebService() {
        if (networkAvailable(app)) {
            Log.i(LOG_TAG, "Calling web service")
            val data = service.getCharactersData(pageNumber).body()?.results ?: emptyList()
            swapiResponsesData.postValue(data)

            if (data.isNotEmpty()) {
                saveDataToExternalFiles(data)
            }
        }
    }

    fun refreshDataFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    private fun createService(): SwapiResponseService {
        val retrofit = Retrofit.Builder()
            .baseUrl(WEB_SERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(SwapiResponseService::class.java)
    }


    /**
     * LOCAL DATA METHODS
     *
     * Nice-to-have functions which are no longer use since I
     * shifted to application to use Rooms.
     *
     * These will remain in case I need them in the future and for
     * reference on implementation.
     */

    private fun saveDataToExternalFiles(characters: List<Character>) {
        // If the application has permission to write to external storage
        // Write content to file
        if (ContextCompat.checkSelfPermission(
                app,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            val json = characterAdapter.toJson(characters)
            FileHelper.saveTextToExternalFiles(app, json)
        }
    }

    private fun saveDataToCache(characters: List<Character>) {
        val json = characterAdapter.toJson(characters)
        FileHelper.saveTextToCache(app, json)
    }

    private fun readDataFromCache(): List<Character> {
        val json = FileHelper.readTextCache(app)

        // If no data exists in cache return blank dummy data
        if (json == null) {
            return emptyList()
        }

        return characterAdapter.fromJson(json) ?: emptyList()
    }

    private fun readDataFromExternalFiles(): List<Character> {
        val json = FileHelper.readTextCache(app)

        // If no data exists in external files return blank dummy data
        if (json == null) {
            return emptyList()
        }

        return characterAdapter.fromJson(json) ?: emptyList()
    }

}