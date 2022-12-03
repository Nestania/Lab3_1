package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.jar.Attributes


class Card_change : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_change)
        title = "Редактирование карточки контакта"
        val Familia = findViewById<EditText>(R.id.editText2)
        val Name = findViewById<EditText>(R.id.editText2)
        val Phone = findViewById<EditText>(R.id.editText1)
        val buttonExit = findViewById<Button>(R.id.button6)
        val buttonKeep = findViewById<Button>(R.id.button3)
        val uid = intent.getLongExtra("Id", 0)
        val dbHelper = DBHelper(this)
        val objects = dbHelper.getById(uid)




        buttonExit.setOnClickListener {
            val intent = Intent(this@Card_change, MainActivity::class.java)
            startActivity(intent)

        }


    }
}