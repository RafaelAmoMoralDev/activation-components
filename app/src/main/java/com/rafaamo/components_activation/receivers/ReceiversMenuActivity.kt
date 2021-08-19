package com.rafaamo.components_activation.receivers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaamo.components_activation.R
import kotlinx.android.synthetic.main.activity_receivers_menu.*

class ReceiversMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receivers_menu)
        setView()
    }

    private fun setView() {
        btn_send_broadcast.setOnClickListener {
            sendBroadcast()
        }

    }

    private fun sendBroadcast() {
        val i = Intent(this, MyReceiver::class.java).setAction("com.rafaamo.action.MY_NOTIFICATION")
        sendBroadcast(i)
    }

}