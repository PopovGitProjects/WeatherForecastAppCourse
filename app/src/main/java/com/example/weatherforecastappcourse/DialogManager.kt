package com.example.weatherforecastappcourse

import android.app.AlertDialog
import android.content.Context

object DialogManager {
    fun locationSettingDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable location?")
        dialog.setMessage("Location disabled, do you want enabled location?")
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close"){_,_ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok"){_,_ ->

        }
    }
}