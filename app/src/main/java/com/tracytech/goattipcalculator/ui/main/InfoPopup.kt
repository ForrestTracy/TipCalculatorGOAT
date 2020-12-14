package com.tracytech.goattipcalculator.ui.main

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.tracytech.goattipcalculator.R


class InfoPopup {
    fun showPopupWindow(context: Context ,view: View, width: Int, height: Int) {
        val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.info_popup, null)
        val width = (width * 0.9).toInt()
        val height = (height * 0.6).toInt()
        val focusable = true // Make Items Outside Of PopupWindow Inactive
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // for ads
        val mAdView = popupView.findViewById(R.id.popupAdView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

//        popupView.setOnTouchListener { v, event -> //Close the window when clicked
//            popupWindow.dismiss()
//            true
//        }
    }
}