package com.tracytech.goattipcalculator.ui.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.tracytech.goattipcalculator.R
import com.tracytech.goattipcalculator.enums.CalculatorType
import kotlinx.android.synthetic.main.fragment_base_calculator.*
import kotlin.math.absoluteValue
import kotlin.math.round

class BaseCalculatorFragment(private var calculatorType: CalculatorType) : Fragment(), View.OnClickListener {

    private lateinit var baseBillInput: TextView
    private lateinit var calculatedTotal: TextView
    private lateinit var eachPersonPaysCalculated: TextView
    private lateinit var eachPersonTotalBreakdown: TextView
    private lateinit var eachPersonTipBreakdown: TextView
    private lateinit var splitBetweenInput: TextView
    private lateinit var tipPercentageInput: TextView
    private lateinit var tipDollarsInput: TextView

    private var splittingBill = false

    private var baseBill : Double = 0.00
    private var tipPercentage : Double = 0.00
    private var tipDollars : Double = 0.00
    
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_base_calculator, container, false)
        setUpButtons(view)
        setupInputs(view)
        setupListeners()
        if (calculatorType == CalculatorType.HAIR) {
            (view.findViewById(R.id.split_title) as TextView).visibility = View.GONE
            (view.findViewById(R.id.no_split_button) as TextView).visibility = View.GONE
            (view.findViewById(R.id.yes_split_button) as TextView).visibility = View.GONE
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val tabs: TabLayout? = (context as Activity).findViewById(R.id.tabs)
        when (calculatorType) {
            CalculatorType.FOOD -> tabs?.setSelectedTabIndicatorColor(resources.getColor(R.color.blue4))
            CalculatorType.HAIR -> tabs?.setSelectedTabIndicatorColor(resources.getColor(R.color.red4))
            CalculatorType.RIDE -> tabs?.setSelectedTabIndicatorColor(resources.getColor(R.color.green3))
        }
    }

    private fun setUpButtons(view: View) {
        when (calculatorType) {
            CalculatorType.FOOD -> setupFoodButtons(view)
            CalculatorType.HAIR -> setupHairButtons(view)
            CalculatorType.RIDE -> setupRideButtons(view)
        }

        (view.findViewById(R.id.superb_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.good_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.fair_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.poor_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.custom_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.yes_split_button) as Button).setOnClickListener(this)
        (view.findViewById(R.id.no_split_button) as Button).setOnClickListener(this)
    }

    private fun setupFoodButtons(view: View) {
        (view.findViewById(R.id.superb_button) as Button).setBackgroundResource(R.drawable.superb_button_food)
        (view.findViewById(R.id.good_button) as Button).setBackgroundResource(R.drawable.good_button_food)
        (view.findViewById(R.id.fair_button) as Button).setBackgroundResource(R.drawable.fair_button_food)
        (view.findViewById(R.id.poor_button) as Button).setBackgroundResource(R.drawable.poor_button_food)
    }

    private fun setupHairButtons(view: View) {
        (view.findViewById(R.id.superb_button) as Button).setBackgroundResource(R.drawable.superb_button_hair)
        (view.findViewById(R.id.good_button) as Button).setBackgroundResource(R.drawable.good_button_hair)
        (view.findViewById(R.id.fair_button) as Button).setBackgroundResource(R.drawable.fair_button_hair)
        (view.findViewById(R.id.poor_button) as Button).setBackgroundResource(R.drawable.poor_button_hair)
    }

    private fun setupRideButtons(view: View) {
        (view.findViewById(R.id.superb_button) as Button).setBackgroundResource(R.drawable.superb_button_ride)
        (view.findViewById(R.id.good_button) as Button).setBackgroundResource(R.drawable.good_button_ride)
        (view.findViewById(R.id.fair_button) as Button).setBackgroundResource(R.drawable.fair_button_ride)
        (view.findViewById(R.id.poor_button) as Button).setBackgroundResource(R.drawable.poor_button_ride)
        (view.findViewById(R.id.no_split_button) as Button).setBackgroundResource(R.drawable.no_focused_ride)
    }

    private fun setupInputs(view: View) {
        baseBillInput = view.findViewById(R.id.base_bill_input)
        baseBillInput.requestFocus()

        calculatedTotal = view.findViewById(R.id.calculated_total_value)
        calculatedTotal.text = getString(R.string.default_total_text)

        eachPersonPaysCalculated = view.findViewById(R.id.each_person_pays_calculated)
        eachPersonTotalBreakdown = view.findViewById(R.id.each_person_total_breakdown)
        eachPersonTipBreakdown = view.findViewById(R.id.each_person_tip_breakdown)

        splitBetweenInput = view.findViewById(R.id.split_input_field)
        splitBetweenInput.text = ""

        tipPercentageInput = view.findViewById(R.id.tip_input_percentage_field)
        tipPercentageInput.isFocusableInTouchMode = true
        tipPercentageInput.text = getString(R.string.initial_zero)

        tipDollarsInput = view.findViewById(R.id.tip_input_dollars_field)
        tipDollarsInput.isFocusableInTouchMode = true
        tipDollarsInput.text = getString(R.string.initial_zero)


    }

    override fun onClick(view: View?) {
        if (view == no_split_button || view == yes_split_button) {
            respondToSplit(view)
            return
        }
        tipPercentage = when (view) {
            superb_button -> 25.00
            good_button   -> 20.00
            fair_button   -> 15.00
            poor_button   -> 10.00
            custom_button -> 0.00
            else -> 15.00
        }
        recalculateTipFields()
        tipPercentageInput.text = tipPercentage.toString()
        if (view == custom_button) tipPercentageInput.requestFocus()
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
        populateEachPersonTotals()
    }

    private fun recalculateTipFields() {
        if (baseBill < 0.01) {
            tipDollars = 0.00
            return
        }
        tipDollars = formatDecimals(baseBill * (tipPercentage / 100)).toDouble()
        tipDollarsInput.text = formatDecimals(tipDollars)
    }

    private fun updateFieldsFromTipDollarsChange() {
        if (baseBill < 0.01) {
            calculatedTotal.text = 0.00.toString()
        }
        tipPercentage = ((tipDollars / baseBill) * 100)
        tipPercentageInput.text = formatDecimals(tipPercentage)
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
        populateEachPersonTotals()
    }

    private fun updateFieldsFromTipPercentageChange() {
        if (baseBill < 0.01) {
            calculatedTotal.text = 0.00.toString()
        }
        tipDollars = formatDecimals(baseBill * (tipPercentage / 100)).toDouble()
        tipDollarsInput.text = formatDecimals(tipDollars)
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
        populateEachPersonTotals()
    }

    private fun updateFieldsFromBaseBillChange() {
        if (baseBill < 0.01) {
            tipDollarsInput.text = "0.00"
            calculatedTotal.text = "0.00"
        }
        tipDollars = formatDecimals(baseBill * (tipPercentage / 100)).toDouble()
        tipDollarsInput.text = formatDecimals(tipDollars)
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
        populateEachPersonTotals()
    }

    private fun setupListeners() {

        baseBillInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(entry: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(entry: Editable) {
                baseBill = entry.trim().toString().toDoubleOrNull() ?: 0.00
                updateFieldsFromBaseBillChange()
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        splitBetweenInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(entry: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(entry: Editable) {
                populateEachPersonTotals()
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        tipDollarsInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(entry: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(entry: Editable) {
                if (tipDollarsInput.hasFocus()) {
                    tipDollars = entry.trim().toString().toDoubleOrNull() ?: 0.00
                    updateFieldsFromTipDollarsChange()
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        tipPercentageInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(entry: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(entry: Editable) {
                if (tipPercentageInput.hasFocus()) {
                    tipPercentage = entry.trim().toString().toDoubleOrNull() ?: 0.00
                    updateFieldsFromTipPercentageChange()
                }
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun formatDecimals(unformattedNumber: Double) : String {
        return String.format("%.2f", unformattedNumber)
    }

    private fun respondToSplit(view: View?) {
        if (view == no_split_button) {
            if (!splittingBill) return
            splittingBill = false
            val noBackground = if (calculatorType == CalculatorType.FOOD) R.drawable.no_focused_food else R.drawable.no_focused_ride
            no_split_button.setBackgroundResource(noBackground)
            yes_split_button.setBackgroundResource(R.drawable.yes_unfocused)
            split_bill_wrapper.visibility = View.INVISIBLE // still takes up layout space vs GONE
        }
        if (view == yes_split_button) {
            if (splittingBill) return
            populateEachPersonTotals()
            splittingBill = true
            no_split_button.setBackgroundResource(R.drawable.no_unfocused)
            val yesBackground = if (calculatorType == CalculatorType.FOOD) R.drawable.yes_focused_food else R.drawable.yes_focused_ride
            yes_split_button.setBackgroundResource(yesBackground)
            splitBetweenInput.requestFocus()
            split_bill_wrapper.visibility = View.VISIBLE
        }
    }

    private fun populateEachPersonTotals() {
        val total = baseBill + tipDollars
        if (total < 0.01) {
            eachPersonPaysCalculated.text = "$0.00"
            eachPersonTotalBreakdown.text = "$0.00"
            eachPersonTipBreakdown.text = "$0.00"
        }
        val splitBetweenCount = split_input_field.text?.toString()?.toDoubleOrNull()
        if (splitBetweenCount == null) {
            eachPersonPaysCalculated.text = "\$_.__"
            eachPersonTotalBreakdown.text = "\$_.__"
            eachPersonTipBreakdown.text = "\$_.__"
            return
        }
        val eachPersonPays = formatDecimals(total / splitBetweenCount)
        eachPersonPaysCalculated.text = "$ $eachPersonPays"
        val totalSplit = formatDecimals(baseBill / splitBetweenCount)
        val tipSplit = formatDecimals(tipDollars / splitBetweenCount)
        eachPersonTotalBreakdown.text = "$$totalSplit bill"
        eachPersonTipBreakdown.text = "$$tipSplit tip"

        var leftover = checkLeftover(total, eachPersonPays.toDouble(), splitBetweenCount.toInt())
        if (leftover > 0) {
            leftover_alert_field.visibility = View.VISIBLE
            leftover_alert_field.text = "* can't be split evenly - Someone can pay ¢$leftover cents less"
        } else if (leftover < 0) {
            leftover_alert_field.visibility = View.VISIBLE
            leftover = leftover.absoluteValue
            leftover_alert_field.text = "* can't be split evenly - Someone has to pay ¢$leftover extra"
        }
    }

    fun checkLeftover(total: Double, eachPersonPays: Double, splitBetweenCount: Int): Int {
        val roughTotal = eachPersonPays.times(splitBetweenCount)
        if (roughTotal.equals(total)) return 0
        return (round((roughTotal.minus(total)) * 100)).toInt()
    }

}