package com.tracytech.goattipcalculator.ui.main

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.tracytech.goattipcalculator.R


class InfoPopup {
    fun showPopupWindow(view: View, width: Int, height: Int) {
        val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.info_popup, null)
        val width = (width * 0.9).toInt()
        val height = (height * 0.6).toInt()
        val focusable = true // Make Items Outside Of PopupWindow Inactive
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // some good stuff here:  https://stackoverflow.com/questions/30969455/android-changing-floating-action-button-color

//        popupView.setOnTouchListener { v, event -> //Close the window when clicked
//            popupWindow.dismiss()
//            true
//        }
    }
}