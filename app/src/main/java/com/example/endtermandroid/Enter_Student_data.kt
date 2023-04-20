package com.example.endtermandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class Enter_Student_data : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var registration: EditText
    private lateinit var course: EditText
    private lateinit var database: DatabaseReference
    private lateinit var submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_student_data)

        name = findViewById(R.id.editText)
        registration = findViewById(R.id.editText1)
        course = findViewById(R.id.editText2)
        submit = findViewById(R.id.submit)

        auth = FirebaseAuth.getInstance()

        submit.setOnClickListener() {


            var name1 = name.text.toString()
            var registration1 = registration.text.toString()
            var course1 = course.text.toString()


            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = User(name1, registration1, course1)
            database.child(registration1).setValue(User).addOnSuccessListener {


                Toast.makeText(this, "Details added", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Gaurd_work::class.java)
                startActivity(intent)
            }.addOnFailureListener {

                Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()

            }
        }
    }
}