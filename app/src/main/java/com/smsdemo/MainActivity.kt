package com.smsdemo

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import android.Manifest.permission
import android.os.Build.VERSION
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    lateinit var message: EditText
    lateinit var phoneNo: EditText
    lateinit var buttonSend: Button
    var userMessage: String = ""
    var userPhoneNumber: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        message = findViewById(R.id.textMessage)
        phoneNo = findViewById(R.id.phoneNumber)
        buttonSend = findViewById(R.id.send)

        buttonSend.setOnClickListener {
            userMessage = message.text.toString()
            userPhoneNumber = phoneNo.text.toString()

            sendSMS(userMessage,userPhoneNumber)
        }
    }

    private fun sendSMS(userMessage: String, userPhoneNumber: String) {
        //check if user has given permisssion
        if (ContextCompat.checkSelfPermission(this, permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,arrayOf(permission.SEND_SMS),1)
        } else {
            val smsManager: SmsManager
            if (VERSION.SDK_INT >= 23){
                 smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userPhoneNumber,null,userMessage,null,null)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1 && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val smsManager: SmsManager
            if (VERSION.SDK_INT >= 23){
                smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userPhoneNumber,null,userMessage,null,null)
            Toast.makeText(applicationContext,"Message sent",Toast.LENGTH_LONG).show()
        }
    }
}