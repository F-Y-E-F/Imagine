package com.example.imagine.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.example.imagine.R

object Texts {


    fun setupOfficialSiteLinkText(pageUrl:String,context: Context): SpannableStringBuilder {
        val officialSiteSpannable = SpannableStringBuilder("Visit official site here")

        val span1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val uri: Uri = Uri.parse(pageUrl)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
        }
        officialSiteSpannable.setSpan(span1, 20, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        officialSiteSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
            20, // start
            24, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        officialSiteSpannable.setSpan(
            UnderlineSpan(), 20,
            24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return officialSiteSpannable
    }

    fun setupTagsText(tags:String,context: Context): SpannableStringBuilder {
        val tagSpannable = SpannableStringBuilder("Tags : $tags")
        tagSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
            7, // start
            tags.length + 7, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return tagSpannable
    }

}