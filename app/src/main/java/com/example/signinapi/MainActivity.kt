package com.example.signinapi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

  lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameId = findViewById<EditText>(R.id.userName)
        val emailId= findViewById<EditText>(R.id.userEmail)
        val phoneId =findViewById<EditText>(R.id.userPhoneNumber)
        val passwordId = findViewById<EditText>(R.id.userPassword)
        val signupButton = findViewById<Button>(R.id.signUp)
        val loginfield = findViewById<TextView>(R.id.Login)

        signupButton.setOnClickListener {
            val name = nameId.text.toString()
            val email = emailId.text.toString()
            val phone = phoneId.text.toString()
            val password = passwordId.text.toString()

            val  userInput = User_Information(name , email , phone, password)
             database = FirebaseDatabase.getInstance().getReference("Users Information")

            database.child(phone).setValue(userInput).addOnSuccessListener {
                Toast.makeText(this, "User Registration Success " , Toast.LENGTH_SHORT).show()
            }

        }
//        loginfield.setOnClickListener{
//            val If_SignUp = Intent(this , SignInPage::class.java )
//            startActivity(If_SignUp)
//        }


    }
}