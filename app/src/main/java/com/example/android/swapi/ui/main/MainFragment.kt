package com.example.android.swapi.ui.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.R
import com.example.android.swapi.data.character.Character
import com.example.android.swapi.data.character.CharacterDao
import com.example.android.swapi.data.character.CharacterDatabase
import com.example.android.swapi.data.character.CharacterRepository
import com.example.android.swapi.ui.shared.SharedViewModel


class MainFragment : Fragment(),
    MainRecyclerAdapter.CharacterItemListener,
    MainRecyclerAdapter.FavoriteButtonListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainRecyclerAdapter
    private lateinit var navController: NavController

    private lateinit var characterRepository: CharacterRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setting this as app compat activity gives access to action bar component
        (requireActivity() as AppCompatActivity).run {
            // this will disable a back arrow on the top nav for this given fragment
            // this arrow was turned on in detail fragment
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        characterRepository = CharacterRepository(requireActivity().application)
        recyclerView = requireView().findViewById(R.id.mainFragmentRecycler)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // this swipe refresh layout will fetch and refresh the recycler when a user pulls down on the recycler
        swipeLayout = requireView().findViewById(R.id.mainSwipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel.swapiData.observe(viewLifecycleOwner, {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = MainRecyclerAdapter(it, this, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })
    }

    override fun onFavoriteButtonClick(character: Character) {
        Log.i(LOG_TAG, "Favorite clicked!")
        characterRepository.updateFavorite(character.characterId)
    }

    override fun onCharacterItemClick(character: Character) {
        // link currently selected character to the view model
        viewModel.selectedCharacter.value = character
        navController.navigate(R.id.action_nav_detail)
    }
}