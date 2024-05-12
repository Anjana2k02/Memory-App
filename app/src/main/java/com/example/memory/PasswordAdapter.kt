package com.example.memory

import DatabaseHelper
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PasswordAdapter(private var passwords: List<Password>, private val context: Context) : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)

    inner class PasswordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val passwordTextView: TextView = itemView.findViewById(R.id.passwordTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.password_item, parent, false)
        return PasswordViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val password = passwords[position]

        holder.nameTextView.text = password.name
        holder.passwordTextView.text = password.password

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdatePasswordActivity::class.java).apply{
                putExtra("password_id", password.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deletepassword(password.id)
            refreshData(db.getAllPassword())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    fun refreshData(newPasswords: List<Password>){
        passwords = newPasswords
        notifyDataSetChanged()
    }


}
