package com.example.imagine.screens

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.children
import com.example.imagine.R
import kotlinx.android.synthetic.main.fragment_export_quality.*


class ExportQualityFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_export_quality, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayListOf(qualityBackButton,qualityText).forEach {
            it.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        listenToButtonCheck()
    }


    private fun listenToButtonCheck(){
        val prefs = requireActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE)

        when(prefs.getInt("exportQuality",100)){
            100-> qualityRadioGroup.check(R.id.quality100)
            80-> qualityRadioGroup.check(R.id.quality80)
            60-> qualityRadioGroup.check(R.id.quality60)
            40-> qualityRadioGroup.check(R.id.quality40)
        }

        qualityRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.quality40 -> prefs.edit().putInt("exportQuality",40).apply()
                R.id.quality60 -> prefs.edit().putInt("exportQuality",60).apply()
                R.id.quality80 -> prefs.edit().putInt("exportQuality",80).apply()
                R.id.quality100 -> prefs.edit().putInt("exportQuality",100).apply()
            }
        }
    }
}