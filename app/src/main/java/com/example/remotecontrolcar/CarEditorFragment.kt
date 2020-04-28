package com.example.remotecontrolcar


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.remotecontrolcar.databinding.FragmentDrivingModeBinding
import kotlinx.android.synthetic.main.fragment_driving_mode.view.*

class CarEditorFragment : Fragment() {

    companion object{

    }

    lateinit var viewModel:ParamViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        //create DataBinding object
        val binding = DataBindingUtil.inflate <FragmentDrivingModeBinding> (inflater, R.layout.fragment_driving_mode, container, false)

        //val dialog = ConfirmDialog(this.context)
        var katjaUp = binding.gearUp
        var katjaDown = binding.gearDown
        katjaUp.setOnTouchListener(TouchListener)
        katjaDown.setOnTouchListener(TouchListener)
        var volant = binding.volant
        volant.setOnTouchListener(TouchListener)

        //show the alert dialog
        //binding.editorConfirmButton.setOnClickListener { dialog!!.show() }

        Log.i("Fragment", "Editor Fragment onCreateView")

        return binding.root
    }

    //object for users to move buttons
    private val TouchListener = View.OnTouchListener { v, event ->

        var positionX = event.rawX
        var positionY = event.rawY
        var xDelta = 0
        var yDelta = 0
        var param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(v.layoutParams)

        when (event.action and MotionEvent.ACTION_MASK){
            MotionEvent.ACTION_DOWN -> {
                when(param){
                    is RelativeLayout.LayoutParams -> {
                        xDelta = (positionX  - param.leftMargin).toInt()
                        yDelta = (positionY  - param.topMargin).toInt()
                        Toast.makeText(this.context, event.x.toString(), Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(this.context, "Try another way", Toast.LENGTH_SHORT).show()
                    }
                }

                true
            }

            MotionEvent.ACTION_MOVE -> {

                when(param){
                    is RelativeLayout.LayoutParams -> {
                        param.leftMargin = (positionX - xDelta).toInt()
                        param.topMargin = (positionY - yDelta).toInt()
                        param.rightMargin = 0
                        param.bottomMargin = 0
                        v.layoutParams = param

                    }
                    else -> {
                        Toast.makeText(this.context, "Try another way", Toast.LENGTH_SHORT).show()
                    }
                }

                true
            }

            MotionEvent.ACTION_UP -> {
                Toast.makeText(this.context, param.leftMargin.toString(), Toast.LENGTH_SHORT).show()
                true
            }

            else -> {
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
                false
            }
        }

        v.invalidate()
        true
    }

    //implementation of alertDialog
//    private fun ConfirmDialog(context: Context?): AlertDialog? {
//        val builder = AlertDialog.Builder(this.context)
//        builder.setMessage(R.string.dialog_save_changes)
//            .setPositiveButton(R.string.dialog_confirm,
//                DialogInterface.OnClickListener { dialog, id ->
//                    findNavController().navigate(R.id.action_carEditorFragment_to_drivingModeFragment)
//                })
//            .setNegativeButton(R.string.dialog_cancel,
//                DialogInterface.OnClickListener { dialog, id ->
//                    // User cancelled the dialog
//                })
//
//        return builder.create()
//    }

}
