package com.tracytech.goattipcalculator.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tracytech.goattipcalculator.R
import com.tracytech.goattipcalculator.enums.CalculatorType

private val TAB_TITLES = arrayOf(
        R.string.food_tab_text,
        R.string.hair_tab_text,
        R.string.ride_tab_text
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BaseCalculatorFragment(CalculatorType.FOOD)
            1 -> BaseCalculatorFragment(CalculatorType.HAIR)
            2 -> BaseCalculatorFragment(CalculatorType.RIDE)
            else -> BaseCalculatorFragment(CalculatorType.FOOD)

        }
    }
}