package com.example.signinapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textview.MaterialTextView

class ContactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Password =  findViewById<MaterialTextView>(R.id.dataPassword)
        val phone = findViewById<MaterialTextView>(R.id.dataPhoneNo)
        val name  = findViewById<MaterialTextView>(R.id.dataName)
        val email = findViewById<MaterialTextView>(R.id.dataEmail)


        // Received data from one SignIn Activity using getStringExtra

        val ReceivePassword = intent.getStringExtra("UserPassword")
        val ReceivePhone  = intent.getStringExtra("UserPhoneNumber")
        val Receivename = intent.getStringExtra("UserName")
        val ReceiveEmail = intent.getStringExtra("UserEmail")


        Log.d("ContactDetailActivity", "Received Password: $ReceivePassword")
        Log.d("ContactDetailActivity", "Received Phone: $ReceivePhone")


        Password.text = ReceivePassword
        phone.text = ReceivePhone
        name.text =  Receivename
        email.text = ReceiveEmail


    }
}