package com.example.android.swapi.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.android.swapi.R

class SplashFragment: Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        displayMainFragment()
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    private fun displayMainFragment() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        navController.navigate(
            R.id.action_nav_main,
            null,
            NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
        )
    }
}