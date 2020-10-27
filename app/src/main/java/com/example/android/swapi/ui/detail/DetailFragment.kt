package com.example.android.swapi.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.android.swapi.LOG_TAG
import com.example.android.swapi.R
import com.example.android.swapi.databinding.DetailFragmentBindingImpl
import com.example.android.swapi.ui.shared.SharedViewModel

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/26/20
 *
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // setting this as app compat activity gives access to action bar component
        (requireActivity() as AppCompatActivity).run {
            // this will create a back arrow on the top nav for this given fragment
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        // this line of code is needed only for fragments
        // you must declare an option menu for a fragment
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

        // the detail fragment was refactored to use data-binding
        // it has an xml data / variable element
        // here I inflate the auto generated detail fragment binding impl class
        // attach the life cycle owner and the named variable to its source: viewModel
        val binding = DetailFragmentBindingImpl.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // now I return the data-bound inflated layout
        return binding.root
    }

    // this function relates to the back arrow being clicked
    // the back arrow is apart of this 'options menu'
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId  == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }
}