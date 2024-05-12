package com.example.memory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import DatabaseHelper
import android.widget.Toast
import com.example.memory.databinding.ActivityAddPasswordBinding

class AddPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPasswordBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val name = binding.nameEmailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val password = Password(0,  name, pass)
            db.insertPassword(password)
            finish()
            Toast.makeText(this, "Password Added", Toast.LENGTH_SHORT).show()
        }
    }
}
