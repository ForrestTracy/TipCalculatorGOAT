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
import kotlinx.android.synthetic.main.fragment_food.*


//private var total : Double = 0.00;

class FoodFragment : Fragment(), View.OnClickListener {

    // TODO should these fields move above class declaration?
    private lateinit var baseBillInput: TextView
    private lateinit var calculatedTotal: TextView
    private lateinit var tipPercentageInput: TextView
    private lateinit var tipDollarsInput: TextView

    private var baseBill : Double = 0.00
    private var tipPercentage : Double = 0.00
    private var tipDollars : Double = 0.00

    companion object { @JvmStatic fun newInstance() = FoodFragment() }

//    enum class QualityOfSvc(val value: String) {   POOR("poor"), FAIR("fair"), GOOD("good"), EXCELLENT("excellent") }
//    enum class QualityOfSvc { POOR, FAIR, GOOD, EXCELLENT, CUSTOM }
//    val qualityToPercentage = mapOf(QualityOfSvc.POOR to 10, QualityOfSvc.FAIR to 15, QualityOfSvc.GOOD to 20, QualityOfSvc.EXCELLENT to 25)
//    val qualityToPercentage = arrayOf(25.00, 20.00, 15.00, 10.00)
//    private val qualityToName = mapOf(QualityOfSvc.POOR to "poor", QualityOfSvc.FAIR to "fair", QualityOfSvc.GOOD to "good", QualityOfSvc.EXCELLENT to "excellent")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_food, container, false)
        setUpButtons(view)
        setupInputs(view)
        setupListeners()
        return view
    }

    private fun setUpButtons(view: View) {
        val superbBtn: Button = view.findViewById(R.id.superb_button)
        val goodBtn: Button = view.findViewById(R.id.good_button)
        val fairBtn: Button = view.findViewById(R.id.fair_button)
        val poorBtn: Button = view.findViewById(R.id.poor_button)
        val customBtn: Button = view.findViewById(R.id.custom_button)

        superbBtn.setOnClickListener(this)
        goodBtn.setOnClickListener(this)
        fairBtn.setOnClickListener(this)
        poorBtn.setOnClickListener(this)
        customBtn.setOnClickListener(this)
    }

    private fun setupInputs(view: View) {
        baseBillInput = view.findViewById(R.id.base_bill_input)

        calculatedTotal = view.findViewById(R.id.calculated_total_value)
        calculatedTotal.text = getString(R.string.default_total_text)

        tipPercentageInput = view.findViewById(R.id.tip_input_percentage_field)
        tipPercentageInput.isFocusableInTouchMode = true
        tipPercentageInput.text = getString(R.string.initial_zero)

        tipDollarsInput = view.findViewById(R.id.tip_input_dollars_field)
        tipDollarsInput.isFocusableInTouchMode = true
        tipDollarsInput.text = getString(R.string.initial_zero)
    }

    override fun onClick(view: View?) {
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
    }

    private fun updateFieldsFromTipPercentageChange() {
        if (baseBill < 0.01) {
            calculatedTotal.text = 0.00.toString()
        }
        tipDollars = formatDecimals(baseBill * (tipPercentage / 100)).toDouble()
        tipDollarsInput.text = formatDecimals(tipDollars)
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
    }

    private fun updateFieldsFromBaseBillChange() {
        if (baseBill < 0.01) {
            tipDollarsInput.text = "0.00"
            calculatedTotal.text = "0.00"
        }
        tipDollars = formatDecimals(baseBill * (tipPercentage / 100)).toDouble()
        tipDollarsInput.text = formatDecimals(tipDollars)
        calculatedTotal.text = "$${formatDecimals(baseBill + tipDollars)}"
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

    override fun onResume() {
        super.onResume()
        val tabs: TabLayout? = (context as Activity).findViewById(R.id.tabs)
        tabs?.setSelectedTabIndicatorColor(resources.getColor(R.color.blue4))
    }

}