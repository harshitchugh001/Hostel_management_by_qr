package com.example.endtermandroid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Student_data1 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var name: TextView
    private lateinit var number: TextView
    private lateinit var course: TextView
    private lateinit var authId: String
    private lateinit var signout:ImageView
    private lateinit var database : DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_data1)

        name = findViewById(R.id.name)
        number = findViewById(R.id.number)
        course = findViewById(R.id.course)
        signout=findViewById(R.id.signout)
        authId = intent.getStringExtra("email").toString()
        var jugaad=intent.getStringExtra("email").toString()
        var email=intent.getStringExtra("email").toString()


        readData(authId)


        val footer = findViewById<BottomNavigationView>(R.id.footer)


        signout.setOnClickListener(){
            auth.signOut()
            startActivity(Intent(this, Selection_page::class.java))
        }

        footer.setOnNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.main -> {
                    val i = Intent(this, Student_data1::class.java)
                    i.putExtra("email",email)
                    startActivity(i)
                    true
                }

                R.id.create -> {


                    true
                }

                R.id.qr -> {
                    val i = Intent(this, ShowQr::class.java)
                    i.putExtra("jugaad",jugaad)
                    startActivity(i)
                    true
                }
                else -> false
            }

        }

    }

    private fun readData(authId: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(authId).get().addOnSuccessListener {
            val firstname = it.child("name1").value
            val lastName = it.child("registration1").value
            val age = it.child("course1").value
            Toast.makeText(this, "Successfuly Read", Toast.LENGTH_SHORT).show()
            name.text = firstname.toString()
            number.text = lastName.toString()
            course.text = age.toString()
        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()


        }
    }
}



