package com.tracytech.goattipcalculator.ui.main

import com.tracytech.goattipcalculator.enums.CalculatorType
import org.junit.Assert.*
import org.junit.Test

internal class BaseCalculatorFragmentTest {

    private val foodFragment = BaseCalculatorFragment(CalculatorType.FOOD)

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

    @Test fun checkLeftOver_over_payment() {
        // given
        val total = 116.15
        val eachPersonPays = 16.60
        val splitBetweenCount = 7

        // when
        val result = foodFragment.checkLeftover(total, eachPersonPays, splitBetweenCount)

        // then
        // 16.60 * 7 = 116.20
        assertEquals(5, result)
    }

    @Test fun checkLeftOver_under_payment() {
        // given
        val total = 45.10
        val eachPersonPays = 15.03
        val splitBetweenCount = 3

        // when
        val result = foodFragment.checkLeftover(total, eachPersonPays, splitBetweenCount)

        // then
        // 15.03 * 3 = 45.09
        assertEquals(-1, result)
    }

    @Test fun checkLeftOver_even_payment() {
        // given
        val total = 100.00
        val eachPersonPays = 25.00
        val splitBetweenCount = 4

        // when
        val result = foodFragment.checkLeftover(total, eachPersonPays, splitBetweenCount)

        // then
        assertEquals(0, result)
    }

    @Test fun checkLeftOver_bad_java_math_over_payment() {
        // given
        val total = 133.40
        val eachPersonPays = 19.06
        val splitBetweenCount = 7

        // when
        val result = foodFragment.checkLeftover(total, eachPersonPays, splitBetweenCount)

        // then
        // 19.06 * 7 = 133.42
        assertEquals(2, result)
    }

    @Test fun checkLeftOver_bad_java_math_even_payment() {
        // given
        val total = 133.50
        val eachPersonPays = 44.50
        val splitBetweenCount = 3

        // when
        val result = foodFragment.checkLeftover(total, eachPersonPays, splitBetweenCount)

        // then
        assertEquals(0, result)
    }
}