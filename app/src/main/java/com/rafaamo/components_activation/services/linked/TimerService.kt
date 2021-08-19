package com.rafaamo.components_activation.services.linked

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TimerService: Service() {

    private val binder = LocalBinder()
    private val _seconds = MutableLiveData(0)
    val seconds = _seconds as LiveData<Int>

    val h = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            _seconds.postValue(_seconds.value!!.plus(1))
            h.postDelayed(this, 2000)
        }
    }

    override fun onCreate() {
        super.onCreate()
        h.post(runnable)
    }


    inner class LocalBinder: Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

}