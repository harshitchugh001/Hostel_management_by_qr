package com.example.endtermandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class Selection_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_page)

        var student=findViewById<CardView>(R.id.cardView)
        var gaurds=findViewById<CardView>(R.id.cardView1)
        student.setOnClickListener(){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        gaurds.setOnClickListener(){
            val i= Intent(this,Gaurd_login::class.java)
            startActivity(i)
        }
    }
}