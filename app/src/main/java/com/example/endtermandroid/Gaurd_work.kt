package com.example.endtermandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class Gaurd_work : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gaurd_work)

        var register=findViewById<CardView>(R.id.register)
        var details=findViewById<CardView>(R.id.details)
        var checkin=findViewById<CardView>(R.id.checkin)
        var checkout=findViewById<CardView>(R.id.checkout)
        var went=findViewById<Button>(R.id.went)

        register.setOnClickListener(){
            val intent= Intent(this,Student_register::class.java)
            startActivity(intent)
        }
        details.setOnClickListener(){
            val i= Intent(this,Enter_Student_data::class.java)
            startActivity(i)
        }
        checkin.setOnClickListener(){
            val i= Intent(this,Check_in::class.java)
            startActivity(i)
        }
        checkout.setOnClickListener(){
            val i= Intent(this,Check_out::class.java)
            startActivity(i)
        }
        went.setOnClickListener(){
            val i=Intent(this,went_page::class.java)
            startActivity(i)
        }
    }
}