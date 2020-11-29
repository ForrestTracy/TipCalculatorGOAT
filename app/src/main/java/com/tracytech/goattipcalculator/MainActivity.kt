package com.tracytech.goattipcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tracytech.goattipcalculator.ui.main.InfoPopup
import com.tracytech.goattipcalculator.ui.main.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    private val TAB_TITLES = arrayOf(
        R.string.food_tab_text,
        R.string.hair_tab_text,
        R.string.ride_tab_text
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, lifecycle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = (resources.getString(TAB_TITLES[position]))
        }.attach()

        val floatingInfoButton: FloatingActionButton = findViewById(R.id.floating_info_button)

        floatingInfoButton.setOnClickListener { view ->
            val infoPopup = InfoPopup()
            infoPopup.showPopupWindow(view, this.window.decorView.width, this.window.decorView.height)
        }
    }
}