package com.example.sos

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun View.showSnackbar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val snackbar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                action(this)
            }.show()
        } else {
            snackbar.show()
        }
    }



    fun Activity.Alert(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Open location")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Toast.makeText(applicationContext, "Turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"clicked No", Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}