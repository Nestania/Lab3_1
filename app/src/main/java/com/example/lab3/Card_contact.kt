package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class Card_contact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact)
        title = "Информация"
        val txt = findViewById<TextView>(R.id.textView2)
        val txtFamilya = findViewById<TextView>(R.id.textView3)
        val txtPhone = findViewById<TextView>(R.id.textView4)
        val buttonExit = findViewById<Button>(R.id.button4)
        val buttonDelete = findViewById<Button>(R.id.button3)
        val buttonChange = findViewById<Button>(R.id.button2)
        val uid = intent.getLongExtra("Id", 0)
        val dbHelper = DBHelper(this)
        val item = dbHelper.getById(uid)


        txt.text = "Фамилия: ${item?.title}"

        buttonExit.setOnClickListener {
            val intent = Intent(this@Card_contact, MainActivity::class.java )
            startActivity(intent)
        }

        buttonChange.setOnClickListener {
            val intent = Intent(this@Card_contact, Card_change::class.java )
            startActivity(intent)
        }

        buttonDelete.setOnClickListener {
            val Contact_id = uid
            dbHelper.remove(Contact_id)
            val intent = Intent(this@Card_contact, MainActivity::class.java )
            startActivity(intent)

        }
    }
}