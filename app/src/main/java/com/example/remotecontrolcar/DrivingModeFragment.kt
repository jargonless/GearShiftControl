package com.example.remotecontrolcar

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.remotecontrolcar.databinding.FragmentDrivingModeBinding


class DrivingModeFragment : Fragment() {

    private val IP = "192.168.4.1"
    private val port = "80"
    private lateinit var esP8266Connector: ESP8266Connector
    private var tinker = -1

    var viewRotation = 0f
    var fingerRotation = 0.0
    var newFingerRotation = 0.0

    var storedInt = 85
    var retrievedInt = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentDrivingModeBinding>(//inflate layout
            inflater,R.layout.fragment_driving_mode, container, false)

        var gearUp = binding.gearUp
        var gearDown = binding.gearDown
        val pedal = binding.pedal
        var volant = binding.volant
        Matrix.ScaleToFit.FILL
        esP8266Connector = ESP8266Connector(this.context!!, this.IP, this.port)

        //get shared Preferences
        val sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
        retrievedInt = sharedPref.getInt("storedInt", -100)


        gearUp.setOnTouchListener(TouchListener)
        gearDown.setOnTouchListener(TouchListener)
        pedal.setOnTouchListener(TouchListener)
        volant.setOnTouchListener(TouchListener)

        //(volant.parent as ConstraintLayout).setOnTouchListener(touchTester)
        return binding.root
    }// end of onCreateView




    private val TouchListener = View.OnTouchListener { v: View, event: MotionEvent ->

        when (v.id){
            R.id.volant ->{
                val x = event.rawX.toDouble() - v.left
                val y = event.rawY.toDouble() - v.top

                val xc:Float = v.width.toFloat() / 2
                val yc:Float = v.height.toFloat() / 2

                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        fingerRotation = Math.toDegrees(Math.atan2(x - xc, v.height - yc - y))
                        v.rotation = fingerRotation.toFloat()

                        true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        newFingerRotation = Math.toDegrees(Math.atan2(x - xc, v.height- yc - y))
                        v.rotation = newFingerRotation.toFloat()

                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        fingerRotation = 0.toDouble()
                        newFingerRotation = 0.toDouble()
                        true
                    }

                    else -> {
                        Log.e(
                            "DragDrop Example",
                            "Unknown action type received by SteeringWheel rotation."
                        )
                        false
                    }
                }
            }//end of volant

            R.id.gearUp -> {
                if (event.action == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.WHITE)
                    esP8266Connector.ShiftUp()
                    esP8266Connector.lockGear()
                }

                else if (event.action == MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.TRANSPARENT)
                    esP8266Connector.unlockGear()
                }
                true
            }
            
             R.id.gearDown -> {
                if (event.action == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.WHITE)
                    esP8266Connector.ShiftDown()
                    esP8266Connector.lockGear()
                }

                else if (event.action == MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.TRANSPARENT)
                    esP8266Connector.unlockGear()
                }
                true
            }

            R.id.pedal -> {
                if (event.action == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.WHITE)
                    esP8266Connector.Accelerator()
                }

                else if (event.action == MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.TRANSPARENT)
                    esP8266Connector.stop()
                }

                true
            }

            else -> {
                Log.e("Driving Error", "Unknown action type received while driving.")
                false
            }
        }
    }

    override fun onDestroyView() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("hanaLeft", storedInt)
            commit()
        }
        super.onDestroyView()
    }

    private val touchTester = View.OnTouchListener { v, event ->
        val x = event.rawX.toDouble()
        val y = event.rawY.toDouble()

        val xc:Float = v.width.toFloat() / 2
        val yc:Float = v.height.toFloat() / 2

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

                true
            }

            MotionEvent.ACTION_UP -> {

                true
            }

            else -> {
                Log.e(
                    "DragDrop Example",
                    "Unknown action type received by SteeringWheel rotation."
                )
                false
            }
        }
    }
}



//Steering wheel thing
//val volant = binding.steeringwheel
//var volantSet: AnimatorSet = AnimatorInflater.loadAnimator(this.context, R.animator.wheel_spin) as AnimatorSet
//volantSet.setTarget(volant)
//volantSet.start()

