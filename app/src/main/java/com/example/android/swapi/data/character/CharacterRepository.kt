package com.example.android.swapi.data.character

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.WEB_SERVICE_URL
import com.example.android.swapi.data.network.NetworkOperationsImpl
import com.example.android.swapi.utilities.FileHelper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
class CharacterRepository(val app: Application) : NetworkOperationsImpl() {
    /**
     * I previously had a SwapiResponseRepository, I found this over complicated things
     * and was forcing me to add needless complexity
     *
     * I think each possible response type from SWAPI should handle its own data and
     * the SwapiCharacterResponse class exists only as a helper to decode the parent
     * object for these list objects
     */
    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val service: CharacterService
    private val characterListType: Type = Types.newParameterizedType(List::class.java, Character::class.java)
    private val characterListAdapter: JsonAdapter<List<Character>> = moshi.adapter(characterListType)

    private val characterDao = CharacterDatabase.getDatabase(app).characterDao()

    private val pageNumber = 1

    val characterData = MutableLiveData<List<Character>>()

    init {
        service = createService()

        refreshData()
    }

    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = characterDao.getAll()

            if(data.isEmpty()) {
                callWebService()
            } else {
                characterData.postValue(data)

                // Dispatchers.Main is saying this context block runs on the main thread
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun updateFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            characterDao.updateFavorite(id)
        }
    }

    // tag this as a worker thread
    // this logic will be ran on a background thread
    @WorkerThread
    private suspend fun callWebService() {
        if (networkAvailable(app)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_SHORT).show()
            }

            Log.i(LOG_TAG, "Calling web service")
            val data = service.getCharactersData(pageNumber).body()?.results ?: emptyList()

            characterData.postValue(data)
            characterDao.insertCharacters(data)
        }
    }

    private fun createService(): CharacterService {
        val retrofit = Retrofit.Builder()
            .baseUrl(WEB_SERVICE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CharacterService::class.java)
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
            val json = characterListAdapter.toJson(characters)
            FileHelper.saveTextToExternalFiles(app, json)
        }
    }

    private fun readDataFromExternalFiles(): List<Character> {
        val json = FileHelper.readTextExternalFiles(app)

        // If no data exists in external files return blank dummy data
        if (json == null) {
            return emptyList()
        }

        return characterListAdapter.fromJson(json) ?: emptyList()
    }

    private fun saveDataToCache(characters: List<Character>) {
        val json = characterListAdapter.toJson(characters)
        FileHelper.saveTextToCache(app, json)
    }

    private fun readDataFromCache(): List<Character> {
        val json = FileHelper.readTextCache(app)

        // If no data exists in cache return blank dummy data
        if (json == null) {
            return emptyList()
        }

        return characterListAdapter.fromJson(json) ?: emptyList()
    }

}