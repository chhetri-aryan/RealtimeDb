package com.example.realtimedb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddEmployeeActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_employee_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().getReference("employees")

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        addButton = findViewById(R.id.addEmp)

        addButton.setOnClickListener {
            addEmployee()
        }

    }

    private fun addEmployee() {

        if (name.text.toString().isEmpty() || email.text.toString().isEmpty() || phone.text.toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val employeeId = database.push().key!!
        val employee = Employee(employeeId, name.text.toString(), email.text.toString(), phone.text.toString())

        database.push().setValue(employee)
            .addOnSuccessListener {
                Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show()
                name.text.clear()
                email.text.clear()
                phone.text.clear()

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add employee", Toast.LENGTH_SHORT).show()
            }
    }
}