package com.example.weatherforecastappcourse

import android.app.AlertDialog
import android.content.Context
import com.example.weatherforecastappcourse.domain.SharedPreference
import com.example.weatherforecastappcourse.models.Settings
import com.example.weatherforecastappcourse.models.SettingsParam
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object DialogManager {
    fun locationSettingDialog(context: Context, listener: OnClickDialogButtonListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.resources.getText(R.string.enable_location))
        dialog.setMessage(context.resources.getText(R.string.location_disabled))
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.resources.getText(
                R.string.close
            )
        ) { _, _ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            listener.onClickDialogButton(null)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: OnClickDialogButtonListener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        val textInputLayout = TextInputLayout(context)
        val edName = TextInputEditText(textInputLayout.context)
        dialog.setTitle(context.resources.getText(R.string.city_name))
        dialog.setView(edName)
        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.resources.getText(
                R.string.close
            )
        ) { _, _ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            listener.onClickDialogButton(edName.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    fun setSettingsDialog(context: Context) {
        val sharedPref = SharedPreference(context)
        val builder = AlertDialog.Builder(context)
        val itemName = arrayOf("mmHg", "ms")
        val checkedItem = sharedPref.getSetParam()
        builder.setMultiChoiceItems(itemName, checkedItem) { _, which, isChecked ->
            checkedItem[which] = isChecked

        }
        val dialog = builder.create()
        dialog.setTitle(context.resources.getText(R.string.app_name))

        dialog.setButton(
            AlertDialog.BUTTON_NEGATIVE,
            context.resources.getText(
                R.string.close
            )
        ) { _, _ ->
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            if (checkedItem[0]){
                sharedPref.saveSet(Settings(pressure = 1, null))
                sharedPref.saveSetParam(SettingsParam(pressParam = true, null))
            }else {
                sharedPref.saveSet(Settings(pressure = -1, null))
                sharedPref.saveSetParam(SettingsParam(pressParam = false, null))
            }
            if (checkedItem[1]){
                sharedPref.saveSet(Settings(pressure = null, wind = 1))
                sharedPref.saveSetParam(SettingsParam(null,windParam = true))
            }else {
                sharedPref.saveSet(Settings(pressure = null, wind = -1))
                sharedPref.saveSetParam(SettingsParam(null,windParam = false))
            }
            dialog.dismiss()
        }
        dialog.show()
    }
}