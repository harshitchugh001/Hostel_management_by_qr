package com.example.endtermandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Student_register : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var signup: Button
    private lateinit var email1: EditText
    private lateinit var pass1: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        signup=findViewById(R.id.button)
        email1=findViewById(R.id.editTextTextPersonName)
        pass1=findViewById(R.id.editTextTextPersonName2)

        auth = FirebaseAuth.getInstance()

        signup.setOnClickListener() {
            var email = email1.text.toString()
            var pass = pass1.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Student register", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this,Gaurd_work::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}