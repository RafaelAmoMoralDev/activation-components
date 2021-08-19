package com.rafaamo.components_activation.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import com.rafaamo.components_activation.R

class SingleOperationService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, R.string.activity_services_service_started, Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, R.string.activity_services_service_destroyed, Toast.LENGTH_SHORT).show()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}