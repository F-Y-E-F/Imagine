package com.example.imagine.helpers

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.imagine.R
import com.example.imagine.mvvm.models.photos.Photo
import com.example.imagine.screens.PhotoPreviewActivity
import com.example.imagine.screens.WallpaperType
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.choose_wallpaper_type.view.*


class Dialogs {

    //--------------------------| Show dialog to chose wallpaper type |---------------------------
    fun showChooseWallpaperTypeDialog(
        activity: PhotoPreviewActivity,
        resource: Bitmap,
        wallpaperManager: WallpaperManager,
        listener: WallpaperType
    ) {
        return activity.let {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.choose_wallpaper_type, null, false)
            val dialog = AlertDialog.Builder(activity).setView(view).create()
            view.homeScreen.setOnClickListener {
                dialog.dismiss()
                listener.onChooseHomeScreen(resource, wallpaperManager)
            }
            view.lockScreen.setOnClickListener {
                dialog.dismiss()
                listener.onChooseLockScreen(resource, wallpaperManager)
            }
            view.homeAndLockScreen.setOnClickListener {
                dialog.dismiss()
                listener.onChooseBothScreens(resource, wallpaperManager)
            }
            dialog.show()
        }
    }


    //get loading dialog
    fun getLoadingDialog(context: Context): AlertDialog? {
        return SpotsDialog.Builder().setContext(context).build()
    }


    //--------------------------| Show snackBar with bigger and bold text |---------------------------
    fun showSnackBar(view: View, text: String) {
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorBackgroundLight))
            .setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))
        val snackbarActionTextView =
            snackbar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarActionTextView.textSize = 16f
        snackbarActionTextView.setTypeface(snackbarActionTextView.typeface, Typeface.BOLD)
        snackbar.show()
    }


    //------------------------------| Show On Long click Dialog |-----------------------------------
    fun showAlertAppDialog(
        activity: FragmentActivity,
        context: Context,
        title: String,
        message: String,
        onPositiveButtonClick: () -> Unit,
    ): AlertDialog {
        return activity.let {
            AlertDialog.Builder(context, R.style.AlertDialogStyle).setTitle(title)
                .setMessage(message)
                .setPositiveButton("Delete") { dialog: DialogInterface, _: Int ->
                    onPositiveButtonClick()
                    dialog.dismiss()
                }.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        }
    }
    //==============================================================================================

}