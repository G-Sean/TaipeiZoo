package com.ws.zoo.main.view.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ws.zoo.R

object CustomDialog {
    fun loadingDialog(context: Context):AlertDialog {
        val builder = AlertDialog.Builder(context,R.style.TransparentDialog);
        builder.setCancelable(false)
        builder.setView(R.layout.dialog_loading_view)
        return builder.create()
    }
}