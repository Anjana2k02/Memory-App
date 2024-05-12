package com.example.memory

import DatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memory.databinding.ActivityUpdateBinding

class UpdatePasswordActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DatabaseHelper
    private var passwordId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = DatabaseHelper(this)

        passwordId = intent.getIntExtra("password_id", -1)
        if(passwordId == -1){
            finish()
            return
        }

        val password = db.getPasswordById(passwordId)
        binding.updatePasswordEditText.setText(password.name)
        binding.updatePasswordEditText.setText(password.password)

        binding.updatesaveButton.setOnClickListener{
            val newName = binding.UpdateNameEmailEditText.text.toString()
            val newPassword = binding.updatePasswordEditText.text.toString()
            val updatePass = Password(passwordId, newName, newPassword)
            db.updatePassword(updatePass)
            finish()
            Toast.makeText(this, "Update Successful !", Toast.LENGTH_SHORT).show()
        }
    }
}