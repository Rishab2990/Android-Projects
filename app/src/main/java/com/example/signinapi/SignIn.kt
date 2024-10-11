package com.example.signinapi

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignIn : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user_Email = findViewById<EditText>(R.id.Login_userEmail)
        val user_Password = findViewById<EditText>(R.id.Login_userPassword)
        val signInButton = findViewById<Button>(R.id.loginButton)

        signInButton.setOnClickListener {
            val userInformation_phone = user_Email.text.toString().trim()
            val userInformation_password = user_Password.text.toString().trim()

            if (userInformation_password.isEmpty() || userInformation_phone.isEmpty()) {
                Toast.makeText(this, "Enter Required Information !", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // To Access the Database and their Reference
            database = FirebaseDatabase.getInstance().getReference("Users Information")
            /*when we use "orderByChild" the program check every node and check the match with equalTo() which is nothing but user
             enter information when i use this we need to use loop because this line code return group of node like one single group
             which have multiple information that's why we must to check password also so that confirm the user authentication and
             move to third screen */
            database.orderByChild("phone").equalTo(userInformation_phone)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

//                        if (!snapshot.exists()) {
//                            Toast.makeText(
//                                this@SignIn,
//                                "User not Registered. Please First Sign Up",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            return
//                        }
                        for (data in snapshot.children) {
                            val databasePassword = data.child("password").value.toString()
                            val userName  = data.child("name").value.toString()
                            val userEmail = data.child("email").value.toString()
                            Log.d("Password Debug", "Stored Password: $databasePassword")
                            Log.d("Snapshot Debug", "Snapshot data: ${snapshot.value}")


                            if (databasePassword == userInformation_password) {
                                val intent = Intent(this@SignIn, ContactDetailActivity::class.java)


                                // To Send data from SignIn Activity to ContactDetailActivity
                                intent.putExtra("UserPassword",databasePassword)
                                intent.putExtra("UserPhoneNumber" , userInformation_phone)
                                intent.putExtra("UserName", userName)
                                intent.putExtra("UserEmail", userEmail)

                                startActivity(intent)

                            } else {
                                Toast.makeText(
                                    this@SignIn,
                                    "Password is wrong ",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return
                            }
                            //  val databasePassword = snapshot.child("password").value.toString()


                        }


                    }


                    override fun onCancelled(error: DatabaseError) {

                        // "Firebase Database Error" - tag : To find message in Locate
                        /*"Error: ${error.message}" - this line print out the message why app crash or operation failed means why
                          app does not connect with database or any kind of issue */

                        Log.d("Firebase Database Error ", "Error : ${error.message}")

                    }

                })


        }
    }

}