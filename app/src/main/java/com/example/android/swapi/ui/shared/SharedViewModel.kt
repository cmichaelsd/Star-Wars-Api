package com.example.android.swapi.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.swapi.data.character.Character
import com.example.android.swapi.data.swapi.SwapiResponseRepository

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/23/20
 *
 */
class SharedViewModel(app: Application): AndroidViewModel(app) {

    // this view model was refactored from MainViewModel
    // to SharedViewModel to persist data across fragments
    private val dataRepo = SwapiResponseRepository(app)

    // this data repo has access to the mutable live data object
    // and can now from the view-model listen to data changes
    val swapiData = dataRepo.swapiResponsesData

    val selectedCharacter = MutableLiveData<Character>()

    fun refreshData() {
        dataRepo.refreshDataFromWeb()
    }
}