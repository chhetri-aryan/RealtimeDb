package com.example.realtimedb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.util.Log

class EmployeeDetailActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbRef = Firebase.database.getReference("employees")

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        email = intent.getStringExtra("email").toString()
        phone = intent.getStringExtra("phone").toString()

        findViewById<TextView>(R.id.empName).text = "Name: $name"
        findViewById<TextView>(R.id.empEmail).text = "Email: $email"
        findViewById<TextView>(R.id.empPhone).text = "Phone: $phone"

        findViewById<Button>(R.id.update).setOnClickListener {
            updateRecord()
        }

        findViewById<Button>(R.id.delete).setOnClickListener {
            deleteRecord(id)
        }
    }

    private fun updateRecord() {
        val intent = Intent(this, UpdateEmployeeActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("name", name)
        intent.putExtra("email", email)
        intent.putExtra("phone", phone)
        startActivity(intent)
    }

    private fun deleteRecord(id: String) {
        dbRef.child(id).removeValue().addOnSuccessListener {
            Log.d("DeleteRecord", "Employee deleted successfully with ID: $id")
            Toast.makeText(this, "Employee deleted successfully", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, FetchActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener { exception ->
            Log.e("DeleteRecord", "Error deleting employee with ID: $id", exception)
            Toast.makeText(this, "Error deleting employee", Toast.LENGTH_SHORT).show()
        }
    }
}