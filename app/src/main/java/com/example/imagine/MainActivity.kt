package com.example.imagine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.imagine.helpers.BottomNavViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val bottomNavViewModel:BottomNavViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(getSharedPreferences("SETTINGS", MODE_PRIVATE).getBoolean("isDarkMode",true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        

        bottomNavViewModel.bottomNavigationVisibility.observe(this){
            bottomNavigationView.visibility = it
        }

            navController.addOnDestinationChangedListener{_,destination,_ ->
            if(destination.id == R.id.settingsFragment || destination.id == R.id.aboutFragment || destination.id == R.id.exportQualityFragment) bottomNavViewModel.hideBottomNav()
            else bottomNavViewModel.showBottomNav()
        }


    }
}