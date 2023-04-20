package com.example.endtermandroid

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class Check_out : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        val showQrButton = findViewById<Button>(R.id.show_qr_button)
        showQrButton.setOnClickListener {
            // Check for camera permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted, start scanning
                startScanning()
            } else {
                // Request camera permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start scanning
                startScanning()
            } else {
                // Camera permission denied, show message
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startScanning() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show()
            } else {
                val number = result.contents.toInt()
                val number1=result.contents.toString()
                // Show dialog box with accept/reject buttons
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Number: $number")
                    .setCancelable(false)
                    .setPositiveButton("Accept", DialogInterface.OnClickListener { dialog, id ->
                        // Add number to database
                        // Replace "entries" with your desired database name
//                        val database = FirebaseDatabase.getInstance().reference.child("entries")
//                        database.push().setValue(number)
                        val database = FirebaseDatabase.getInstance().getReference("entries")

                        database.child(number1).setValue(number).addOnSuccessListener {


                            Toast.makeText(this, "Go out", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {

                            Toast.makeText(this, "scan again", Toast.LENGTH_SHORT).show()

                        }
                    })
                    .setNegativeButton("Reject", DialogInterface.OnClickListener { dialog, id ->
                        // Do nothing

                    })
                val alert = builder.create()
                alert.show()
            }
        }
    }
}