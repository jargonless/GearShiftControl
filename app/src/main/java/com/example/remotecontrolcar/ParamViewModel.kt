package com.example.remotecontrolcar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParamViewModel: ViewModel() {
    var katjaUpleftMargin = MutableLiveData<Int>()
    var katjaUptopMargin = MutableLiveData<Int>()

    val katjaDownleftMargin = MutableLiveData<Int>()
    val katjaDowntopMargin = MutableLiveData<Int>()


    init {

        Log.i("GameViewModel", "GameViewModel created!")
        katjaUptopMargin.value = 1146
        katjaUpleftMargin.value = 50

    }





}
