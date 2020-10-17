package com.tracytech.goattipcalculator.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.tracytech.goattipcalculator.R
import kotlinx.android.synthetic.main.fragment_food.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var total : Double = 0.00;

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment(), View.OnClickListener {

    private lateinit var calculatedTotal: TextView
    private lateinit var tipPercentageInput: TextView
    private lateinit var tipDollarsInput: TextView

//    enum class QualityOfSvc(val value: String) {
//        POOR("poor"), FAIR("fair"), GOOD("good"), EXCELLENT("excellent")
//    }

    enum class QualityOfSvc { POOR, FAIR, GOOD, EXCELLENT, CUSTOM }

//    val qualityToPercentage = mapOf(QualityOfSvc.POOR to 10, QualityOfSvc.FAIR to 15, QualityOfSvc.GOOD to 20, QualityOfSvc.EXCELLENT to 25)
    val qualityToPercentage = arrayOf(25.00, 20.00, 15.00, 10.00)
    private val qualityToName = mapOf(QualityOfSvc.POOR to "poor", QualityOfSvc.FAIR to "fair", QualityOfSvc.GOOD to "good", QualityOfSvc.EXCELLENT to "excellent")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_food, container, false)
        val superbBtn: Button = view.findViewById(R.id.superb_button)
        val goodBtn: Button = view.findViewById(R.id.good_button)
        val fairBtn: Button = view.findViewById(R.id.fair_button)
        val poorBtn: Button = view.findViewById(R.id.poor_button)
        val customBtn: Button = view.findViewById(R.id.custom_button)
        calculatedTotal = view.findViewById(R.id.calculated_total_value)
        calculatedTotal.text = getString(R.string.default_total_text)
//        tipPercentageTextView = view.findViewById(R.id.tip_value)
        tipPercentageInput = view.findViewById(R.id.tip_input_percentage_field)
        tipPercentageInput.isFocusableInTouchMode = true
        tipPercentageInput.text = "  "
        tipDollarsInput = view.findViewById(R.id.tip_input_dollars_field)
        tipDollarsInput.isFocusableInTouchMode = true
        tipDollarsInput.text = "  "

        superbBtn.setOnClickListener(this)
        goodBtn.setOnClickListener(this)
        fairBtn.setOnClickListener(this)
        poorBtn.setOnClickListener(this)
        customBtn.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View?) {
        var tipPercentage = when (view) {
            superb_button -> 25.00
            good_button   -> 20.00
            fair_button   -> 15.00
            poor_button   -> 10.00
            custom_button -> 0.00
            else -> 15.00
        }
        total = calculateTotal(tipPercentage)
        tipPercentageInput.text = "$tipPercentage"
        if (view == custom_button) {
            tipPercentageInput.text = ""
            tipPercentageInput.requestFocus()
        }
        calculatedTotal.text = "$ $total"
    }

    // TODO recalculate every time tip field or base_bill field is changed
    fun calculateTotal(tipPercentage: Double): Double {
        var baseBill: Int
        // TODO add numeric check on base_bill_input
        if (base_bill_input != null && !base_bill_input.text.isNullOrEmpty()) {
            baseBill = base_bill_input.text.toString().trim().toInt()
        } else return 0.00
        val tipAmount : Double = baseBill * (tipPercentage / 100)
        return baseBill + tipAmount
    }

    override fun onResume() {
        super.onResume()
        val tabs: TabLayout? = (context as Activity).findViewById(R.id.tabs)
        tabs?.setSelectedTabIndicatorColor(resources.getColor(R.color.blue4))
    }

    companion object {
        @JvmStatic
        fun newInstance() = FoodFragment()
    }

}