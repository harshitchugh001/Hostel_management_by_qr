package com.example.endtermandroid

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

class ShowQr : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var number: TextView
    private lateinit var course: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private lateinit var signout:ImageView
    private lateinit var database : DatabaseReference
    private lateinit var qrCodeImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_qr)

        name = findViewById(R.id.name)
        number = findViewById(R.id.number)
        course = findViewById(R.id.course)
        qrCodeImageView=findViewById(R.id.idIVQrcode)
        signout=findViewById(R.id.signout)
        authId = intent.getStringExtra("jugaad").toString()
        var email=intent.getStringExtra("jugaad").toString()

        readData(authId)
        Toast.makeText(this, authId, Toast.LENGTH_SHORT).show()
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
            val qrCodeBitmap = generateQRCode( lastName as String)
            if (qrCodeBitmap != null) {
                qrCodeImageView.setImageBitmap(qrCodeBitmap)
            } else {
                // handle the case where QR code generation fails
                Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()


        }
    }
    fun generateQRCode( regNumber: String): Bitmap? {
        val size = 512 // set QR code size

        try {
            // create a byte array from name, regNumber and batch
            val data = "$regNumber".toByteArray(Charsets.UTF_8)

            // set QR code parameters
            val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H

            // create QR code writer instance and encode the data
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(String(data), BarcodeFormat.QR_CODE, size, size, hints)

            // create a bitmap image from the bit matrix
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }

            // return the generated QR code bitmap
            return bmp
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}