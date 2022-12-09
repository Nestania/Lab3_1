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
        val txtlastName = findViewById<TextView>(R.id.textViewlastName)
        val txtfirstName = findViewById<TextView>(R.id.textViewfirstName)
        val txtPhone = findViewById<TextView>(R.id.textViewPhone)
        val txtbirthday = findViewById<TextView>(R.id.textViewbirthday)
        val buttonExit = findViewById<Button>(R.id.button4)
        val buttonDelete = findViewById<Button>(R.id.button3)
        val buttonChange = findViewById<Button>(R.id.button2)
        val uid = intent.getLongExtra("Id", 0)
        val dbHelper = DBHelper(this)
        val item = dbHelper.getById(uid)

        txtfirstName.text = "Имя: ${item?.lastName}"
        txtlastName.text = "Фамилия: ${item?.firstName}"
        //txtPhone.text = "Номер телефона: ${item?.phone}"
        txtbirthday.text = "Дата рождения: ${item?.birthday}"


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