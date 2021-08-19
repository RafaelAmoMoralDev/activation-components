package com.rafaamo.components_activation.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaamo.components_activation.R
import com.rafaamo.components_activation.services.linked.TimerActivity
import kotlinx.android.synthetic.main.activity_services_menu.*

const val SCHEDULED_SERVICE_ID = 123

class ServicesMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_menu)
        setView()
    }

    private fun setView() {
        btn_linked_intent.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }

        btn_single_operation_intent.setOnClickListener {
            singleOperation()
        }

        btn_schedule_intent.setOnClickListener {
            scheduleIntent()
        }
    }

    private fun singleOperation() {
        val i = Intent(this, SingleOperationService::class.java)
        startService(i)
    }

    private fun scheduleIntent() {
        /* JobSchedule tiene un mínimo para cada repetición que es de 15m, por lo que para mostrar
        * este tipo de activación de un servicio, agregamos el método setBackoffCriteria y agregamos
        *  un mínimo de latencia de 3s. */
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(SCHEDULED_SERVICE_ID, ComponentName(this, ScheduledService::class.java))
        val job = jobInfo.setRequiresCharging(false)
            .setBackoffCriteria(0, JobInfo.BACKOFF_POLICY_LINEAR)
            .setMinimumLatency(3000)
            .build()
        jobScheduler.schedule(job)
    }

    override fun onDestroy() {
        super.onDestroy()
        val i = Intent(this, SingleOperationService::class.java)
        stopService(i)

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(SCHEDULED_SERVICE_ID)
    }

}