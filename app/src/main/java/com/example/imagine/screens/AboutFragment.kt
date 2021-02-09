package com.example.imagine.screens

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imagine.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayListOf<View>(aboutText, aboutBackButton).forEach {
            it.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        checkItText.paintFlags = checkItText.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        checkItText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/api/docs/"))
            startActivity(intent)
        }
    }


}