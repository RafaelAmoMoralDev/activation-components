package com.rafaamo.components_activation.services.linked

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rafaamo.components_activation.R
import kotlinx.android.synthetic.main.activity_timer.*

/**
 * Una de las formas de inciar un servicio es a través del método bindService().
 * Para más información sobre servicios enlazados https://developer.android.com/guide/components/services?hl=es-419#CreatingBoundService
 */
class TimerActivity : AppCompatActivity() {

    private var timerService: TimerService? = null
    private var mBound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            timerService = binder.getService()
            mBound = true
            listenTimer()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
    private val observer = Observer<Int> {
        timer.text = it.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        setView()
    }

    private fun setView() {
        btn_start.setOnClickListener {
            startServiceIntent()
        }

        btn_end.setOnClickListener {
            stopTimer()
        }
    }

    private fun startServiceIntent() {
        Intent(this, TimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun stopTimer() {
        timerService?.seconds?.removeObserver(observer)
        if (mBound) unbindService(connection)
        mBound = false
    }

    private fun listenTimer() {
        timerService?.seconds?.observe(this, observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

}