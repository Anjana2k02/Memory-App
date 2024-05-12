package com.example.memory

import DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memory.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var passwordAdapter: PasswordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        passwordAdapter = PasswordAdapter(db.getAllPassword(), this)

        binding.passwordRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.passwordRecyclerView.adapter = passwordAdapter

        binding.addButton.setOnClickListener {
            // Create an Intent to start AddPasswordActivity
            val intent = Intent(this, AddPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()
        passwordAdapter.refreshData(db.getAllPassword())
    }
}
