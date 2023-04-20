package com.example.endtermandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.*

class went_page : AppCompatActivity() {

    private lateinit var dataTextView: TextView
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_went_page)

        dataTextView = findViewById(R.id.data_text_view)

        // Replace "entries" with your desired database name
        databaseReference = FirebaseDatabase.getInstance().getReference("entries")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get all the entries as a map
                val entries = dataSnapshot.value as Map<String, Any>?

                // Display the entries in the TextView
                val stringBuilder = StringBuilder()
                for (entry in entries.orEmpty()) {
//                    stringBuilder.append(entry.key).append(": ").append(entry.value).append("\n")
                    stringBuilder.append(entry.value).append("\n")
                }
                dataTextView.text = stringBuilder.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}