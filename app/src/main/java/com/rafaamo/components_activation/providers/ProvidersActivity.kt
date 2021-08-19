package com.rafaamo.components_activation.providers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rafaamo.components_activation.R
import kotlinx.android.synthetic.main.activity_providers.*


class ProvidersActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            getContacts()
        } else {
            Toast.makeText(this, R.string.activity_providers_grant_permissions, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)
        setView()
    }

    private fun setView() {
        btn_contacts.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(Manifest.permission.READ_CONTACTS) -> {
                getContacts()
            }
            else -> { requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
        }
    }

    private fun getContacts() {
        var contacts: ArrayList<String>? = null
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        ) ?: return
        while (cursor.moveToNext()) {
            if (contacts == null) {
                contacts = ArrayList()
            }

            contacts.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)))
        }
        cursor.close()

        if (!contacts.isNullOrEmpty()) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                contacts.toList()
            )
            list_view.adapter = adapter
        }
    }

}