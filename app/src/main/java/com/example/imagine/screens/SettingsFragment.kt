package com.example.imagine.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.imagine.R
import com.example.imagine.helpers.Dialogs
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val dialogs = Dialogs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nightModeSwitch.isChecked = requireActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE).getBoolean("isDarkMode",true)
        arrayListOf<View>(settingsBackButton, settingsText).forEach {
            it.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        setupDarkMode()
        setupAppInfo()
        setupAbout()
    }

    //dodac opcje do jakosci exportu zdjecia
    private fun setupDarkMode() {
        val sp = requireActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE)
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sp.edit().apply {
                if (isChecked) {
                    putBoolean("isDarkMode", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    apply()
                } else {
                    putBoolean("isDarkMode", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    apply()
                }
            }
        }
    }


    private fun setupAppInfo(){
        appVersionButton.setOnClickListener {
            (dialogs.showAlertAppDialog(requireActivity(),requireContext(),"App version", "v1.0","Ok"){}).show()
        }
    }


    private fun setupAbout(){
        aboutButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }


}