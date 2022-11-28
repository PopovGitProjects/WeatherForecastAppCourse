package com.example.weatherforecastappcourse.presentation.dialog

import android.app.AlertDialog
import android.content.Context
import com.example.weatherforecastappcourse.presentation.interfaces.OnClickDialogButtonListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object DialogManager {
    fun locationSettingDialog(context: Context, listener: OnClickDialogButtonListener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Enable location?")
        dialog.setMessage("Location disabled, do you want enabled location?")
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close"){_,_ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok"){_,_ ->
            listener.onClickDialogButton(null)
            dialog.dismiss()
        }
        dialog.show()
    }
    fun searchByNameDialog(context: Context, listener: OnClickDialogButtonListener){
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
//        val edName = EditText(context)
        val textInputLayout = TextInputLayout(context)
        val edName = TextInputEditText(textInputLayout.context)
        dialog.setTitle("City name:")
        dialog.setView(edName)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close"){_,_ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok"){_,_ ->
            listener.onClickDialogButton(edName.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }
}