package com.example.realtimedb

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FetchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeList: ArrayList<Employee>
    private lateinit var debRef: DatabaseReference
    private lateinit var loading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fetch)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        employeeList = arrayListOf()
        loading = findViewById(R.id.loading)
        getEmployeesData()
    }

    private fun getEmployeesData() {
        recyclerView.visibility = View.GONE
        loading.visibility = View.VISIBLE
        debRef = Firebase.database.getReference("employees")
        debRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                employeeList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(Employee::class.java)
                        employeeList.add(empData!!)
                    }
                    val mAdapter = EmployeeAdapter(employeeList)
                    recyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmployeeAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val emp = employeeList[position]
                            val intent =
                                Intent(this@FetchActivity, EmployeeDetailActivity::class.java)
                            intent.putExtra("id", emp.id)
                            intent.putExtra("name", emp.name)
                            intent.putExtra("email", emp.email)
                            intent.putExtra("phone", emp.phone)
                            finish()
                            startActivity(intent)

                        }
                    })
                }
                recyclerView.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FetchActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}