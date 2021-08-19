package com.rafaamo.components_activation.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast
import com.rafaamo.components_activation.R


class ScheduledService: JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Toast.makeText(this, R.string.activity_services_scheduled_service_started, Toast.LENGTH_SHORT).show()
        //Terminamos el trabajo para poder mostrar cómo el servicio se repite.
        jobFinished(jobParameters, true)
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }

}