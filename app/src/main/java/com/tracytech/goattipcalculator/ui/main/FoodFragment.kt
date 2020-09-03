package com.tracytech.goattipcalculator.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.tracytech.goattipcalculator.R
import kotlinx.android.synthetic.main.fragment_food.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val calcTotalButton = (Button) findViewById(R.id.calculate_total_button);
        // might be a good solution example :  https://gist.github.com/EmmanuelGuther/1fde5cfbd1cdcd21cd852e3bb5716e02
//        R.id.calculate_total_button.setOnClickListener() {
//            calculateTotal()
//        }
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_food, container, false)

//        val view: View = inflater!!.inflate(R.layout.fragment, container, false)
        val view: View = inflater.inflate(R.layout.fragment_food, container, false)
//        val btn: Button = view.find(R.id.calculate_total_button)
        val btn: Button = view.findViewById(R.id.calculate_total_button)
        btn.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        println("hello there ******************** ")
    }

    fun calculateTotal(): Int {
        val baseBill = base_bill_input.text.toString().trim().toInt()
        val tipAmount = service_quality_input.text.toString().trim().toInt()
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

//    textField.setEndIconOnClickListener {
//        // Respond to end icon presses
//    }
//
//    textField.addOnEditTextAttachedListener {
//        // If any specific changes should be done when the edit text is attached (and
//        // thus when the trailing icon is added to it), set an
//        // OnEditTextAttachedListener.
//
//        // Example: The clear text icon's visibility behavior depends on whether the
//        // EditText has input present. Therefore, an OnEditTextAttachedListener is set
//        // so things like editText.getText() can be called.
//    }
//
//    textField.addOnEndIconChangedListener
//    {
//
//    }

}