package com.rafaamo.components_activation.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.rafaamo.components_activation.R
import kotlinx.android.synthetic.main.activity_activities_menu.*

const val CHANNEL_ID = "my_channel_01"
const val CHANNEL_NAME = "my_channel"
const val NOTIFICATION_ID = 123

class ActivitiesMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_menu)
        setView()
    }

    private fun setView(){
        btn_explicit_intent.setOnClickListener {
            startExplicitIntent()
        }

        btn_implicit_intent.setOnClickListener {
            startImplicitIntent()
        }

        btn_chooser_intent.setOnClickListener {
            startChooserIntent()
        }

        btn_pending_intent.setOnClickListener {
            launchPendingIntent()
        }

    }

    private fun startExplicitIntent() {
        val i = Intent(this, FirstActivity::class.java)
        startActivity(i)
    }

    private fun startImplicitIntent() {
        /*Si queremos que una de nuestras actividades pueda iniciarse desde un intent implícito, debemos
        de añadir la categoría DEFAULT en su intent filter dentro del manifiesto.*/
        val intent = Intent(Intent.ACTION_VIEW)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.activity_activities_menu_intent_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startChooserIntent() {
        val getContentIntent = Intent(Intent.ACTION_VIEW)
        val candidates: List<ResolveInfo> = packageManager.queryIntentActivities(getContentIntent, 0)

        if (!candidates.isNullOrEmpty()) {
            val targets: ArrayList<Intent> = ArrayList()

            for (candidate in candidates) {
                val name = candidate.activityInfo.name
                val packageName = candidate.activityInfo.packageName
                /*Aparentemente el icono en una actividad no es sobreescrito aunque se especifique el
                atributo icon en la actividad dentro del manifest.*/
                val icon = candidate.activityInfo.icon
                var intent = Intent()
                intent.component = ComponentName(packageName, name)
                if (packageName == getPackageName()) {
                    intent = LabeledIntent(intent, packageName, name, icon)
                }
                targets.add(intent)
            }

            val chooser = Intent.createChooser(getContentIntent, getString(R.string.activity_activities_menu_btn_chooser_intent_title))
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(arrayOf<Parcelable>()))
            startActivity(chooser)
        } else {
            Toast.makeText(this, R.string.activity_activities_menu_intent_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchPendingIntent() {
        /* La notificación no es lo importante dentro de este apartado simplemente es una forma de lanzar
        * nuestro Pending Intent.
        * Para leer más acerca de las Pending Intents https://developer.android.com/guide/components/intents-filters?hl=es-419#PendingIntent
        */
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            mChannel.enableVibration(true)
            notificationManager.createNotificationChannel(mChannel)

            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setAutoCancel(true)

            val notifyIntent = Intent(this, PendingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(notifyPendingIntent)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

}