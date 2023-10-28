package com.smsdemo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MailActivity : AppCompatActivity() {

    lateinit var mailMessage : EditText
    lateinit var mailSubject : EditText
    lateinit var mailAddress : EditText
    lateinit var sendMail: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)
        configureComponents()
        sendMail.setOnClickListener {
        sendButtonClicked()
    }
    }

    private fun sendButtonClicked() {
        val userAddress = mailAddress.text.toString()
        val userSubject = mailSubject.text.toString()
        val userMessage = mailMessage.text.toString()

        val emailAddresses = arrayOf(userAddress)
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.putExtra(Intent.EXTRA_EMAIL,emailAddresses)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,userSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT,userMessage)

        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent,"Choose an app"))
        }
    }

    private fun configureComponents() {
        mailMessage = findViewById(R.id.emailTextMessage)
        mailSubject = findViewById(R.id.emailSubject)
        mailAddress = findViewById(R.id.emailAddress)
        sendMail = findViewById(R.id.sendEmail)
    }
}