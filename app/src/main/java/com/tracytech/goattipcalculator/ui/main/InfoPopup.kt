package com.tracytech.goattipcalculator.ui.main

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.tracytech.goattipcalculator.MainActivity
import com.tracytech.goattipcalculator.R


class InfoPopup {
    fun showPopupWindow(mainActivity: MainActivity, view: View, width: Int, height: Int) {
        val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.info_popup, null)
        val width = (width * 0.9).toInt()
        val height = (height * 0.65).toInt()
        val focusable = true // Make Items Outside Of PopupWindow Inactive
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // for ads
        MobileAds.initialize(mainActivity) {}

        // small banner ads
        val mAdView = popupView.findViewById(R.id.adViewBanner) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // large interstitial ads
        val interstitialAd = InterstitialAd(mainActivity)
        interstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"  // TODO - this is the test ID. Need to put in my real one
        interstitialAd.loadAd(AdRequest.Builder().build())
        val showBigAdButton: Button = popupView.findViewById(R.id.show_interstitial_ad_button)
        showBigAdButton.setOnClickListener { if (interstitialAd.isLoaded) interstitialAd.show() }

//        popupView.setOnTouchListener { v, event -> //Close the window when clicked
//            popupWindow.dismiss()
//            true
//        }
    }

    // TODO probably delete this method
    private fun getBGColor(position: Int) : Int {
        return when (position) {
            0 -> R.color.blue4
            1 -> R.color.red3
            2 -> R.color.green3
            else -> R.color.blue4
        }
    }
}