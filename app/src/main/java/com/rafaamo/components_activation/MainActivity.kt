package com.rafaamo.components_activation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaamo.components_activation.activities.ActivitiesMenuActivity
import com.rafaamo.components_activation.providers.ProvidersActivity
import com.rafaamo.components_activation.receivers.ReceiversMenuActivity
import com.rafaamo.components_activation.services.ServicesMenuActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView(){
        btn_activities.setOnClickListener {
            startActivity(Intent(this, ActivitiesMenuActivity::class.java))
        }

        btn_services.setOnClickListener {
            startActivity(Intent(this, ServicesMenuActivity::class.java))
        }

        btn_receivers.setOnClickListener {
            startActivity(Intent(this, ReceiversMenuActivity::class.java))
        }

        btn_providers.setOnClickListener {
            startActivity(Intent(this, ProvidersActivity::class.java))
        }
    }

}