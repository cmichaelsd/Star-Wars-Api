package com.example.android.swapi.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.android.swapi.R

const val PERMISSION_REQUEST_CODE = 1001

class SplashFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // If the application has permission to write to external storage
        // Go to main fragment
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            displayMainFragment()
        } else {
            // Ask for user permission, this will trigger a callback
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }


        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // If the callback gives use the request code for out permission
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // If the granted results are not empty and the first element in the granted array
            // is permission granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Send the user to the main fragment
                displayMainFragment()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
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