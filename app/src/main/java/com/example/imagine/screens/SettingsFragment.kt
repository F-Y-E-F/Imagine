package com.example.imagine.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.imagine.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {


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
    }


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
}