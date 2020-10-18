package com.tracytech.goattipcalculator.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tracytech.goattipcalculator.R

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
        // position1 = food  //  position2 = hair  //  position3 = ride
        return when (position) {
            0 -> FoodFragment.newInstance()
            1 -> PlaceholderFragment.newInstance(position + 1)
            else -> PlaceholderFragment.newInstance(position + 1)

        }
    }
}