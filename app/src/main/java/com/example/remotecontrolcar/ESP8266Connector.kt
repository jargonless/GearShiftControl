package com.example.remotecontrolcar

import android.content.Context
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue


class ESP8266Connector(context: Context, ip: String, port: String) {

    var ctx: Context
    var root: String

    val keyStop = "S"
    val keyAccelerator = "A"
    val keyGearUp = "U"
    val keyGearDown = "D"
    val keyBreak = "b"
    val keyLock = "1"
    val keyUnlock = "0"

    init {
        this.ctx = context
        this.root = "http://${ip}:${port}"
    }

    fun ShiftUp (){
        sendRequest(keyGearUp)

    }
    
     fun ShiftDown(){
        sendRequest(keyGearDown)

    }

    //prevent gearshifting until clutch is released
    fun lockGear (){
        sendRequest(keyLock)
    }

    //clutch released, ready to shift
    fun unlockGear (){
        sendRequest(keyUnlock)
    }

    fun Accelerator (){
        sendRequest(keyAccelerator)
    }

    fun stop(){
        sendRequest(keyStop)
    }


    private fun sendRequest (stateValue: String){
        var url = "${this.root}/?State=${stateValue}"
        var stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>{ response ->

            Log.i("Send Request Response", response.toString())

        }, Response.ErrorListener { error -> Log.i("Send Request Error: ", error.toString()) })

        stringRequest.setShouldCache(false)
        getRequestQueue().add(stringRequest)
    }


    private fun getRequestQueue(): RequestQueue {
        var mRequestQueue = Volley.newRequestQueue(ctx)
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx)
        }
        return mRequestQueue
    }



}
