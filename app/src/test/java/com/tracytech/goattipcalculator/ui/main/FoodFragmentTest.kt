package com.tracytech.goattipcalculator.ui.main

import org.junit.Assert.*
import org.junit.Test

internal class FoodFragmentTest {

    private val foodFragment = FoodFragment()

    @Test fun formatDecimals_noDecimals() {
        // given
        val unformattedNumber = 987.toDouble()

        // when
        val result = foodFragment.formatDecimals(unformattedNumber)

        // then
        assertEquals("987.00", result)
    }

    @Test fun formatDecimals_oneDecimal() {
        // given
        val unformattedNumber = 987.0

        // when
        val result = foodFragment.formatDecimals(unformattedNumber)

        // then
        assertEquals("987.00", result)
    }

    @Test fun formatDecimals_twoDecimals() {
        // given
        val unformattedNumber = 987.00

        // when
        val result = foodFragment.formatDecimals(unformattedNumber)

        // then
        assertEquals("987.00", result)
    }

    @Test fun formatDecimals_tonsOfFreakinDecimals() {
        // given
        val unformattedNumber = 987.00000923498001

        // when
        val result = foodFragment.formatDecimals(unformattedNumber)

        // then
        assertEquals("987.00", result)
    }
}