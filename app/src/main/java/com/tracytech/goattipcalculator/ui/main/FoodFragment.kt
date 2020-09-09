package com.tracytech.goattipcalculator.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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


    private var tipPercentage: Double = 15.00

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
        val btn: Button = view.findViewById(R.id.calculate_total_button)
        calculatedTotal = view.findViewById(R.id.calculated_total_view)
        calculatedTotal.text = getString(R.string.total_text)

        val dropdown = view.findViewById<Spinner>(R.id.quality_drop_down)
        // might need something like this: https://stackoverflow.com/a/5357531/3288258  for the input numbers field
        val dropDownOptions = arrayOf(qualityToName[QualityOfSvc.EXCELLENT], qualityToName[QualityOfSvc.GOOD], qualityToName[QualityOfSvc.FAIR],  qualityToName[QualityOfSvc.POOR])
        val adapter = context?.let {
            ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, dropDownOptions)
        }
        dropdown.adapter = adapter

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipPercentage = qualityToPercentage[position]
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                tipPercentage = 15.00
            }
        }


        btn.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        total = calculateTotal()
        calculatedTotal.text =  total.toString()
    }

    fun calculateTotal(): Double {
        val baseBill = base_bill_input.text.toString().trim().toInt()
        val tipAmount : Double = baseBill * (tipPercentage / 100)
        return baseBill + tipAmount
    }

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment.
         */
        @JvmStatic
        fun newInstance() =
            FoodFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }

}