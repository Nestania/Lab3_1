package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class Card_change : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_change)
        title = "Редактирование карточки контакта"
        val lastName = findViewById<EditText>(R.id.editTextlastName)
        val firstName = findViewById<EditText>(R.id.editTextfirstName)
        val Phone = findViewById<EditText>(R.id.editTextPhone)
        val birthday = findViewById<EditText>(R.id.editTextbirthday)


        val buttonExit = findViewById<Button>(R.id.button6)
        val buttonKeep = findViewById<Button>(R.id.button5)
        val uid = intent.getLongExtra("Id", 0)
        val dbHelper = DBHelper(this)
        val `object` = dbHelper.getById(uid)
        val intent = intent
        val id = intent.getLongExtra("Id", 0)
        val allData = dbHelper.getById(id)

        lastName.setText(allData?.lastName)

        buttonExit.setOnClickListener {
            val intent = Intent(this@Card_change, MainActivity::class.java)
            startActivity(intent)

        }

        buttonKeep.setOnClickListener {
            val familia = lastName.text.toString()
            val name = firstName.text.toString()
            val phone = Phone.text.toString()
            val birthday = birthday.text.toString()
            if (id == 0L) {
                val upd = dbHelper.add(
                    familia,
                    name,
                    phone,
                    birthday
                )

            } else {
                val upd = dbHelper.update(
                    id,
                    familia,
                    name,
                    phone,
                    birthday
                )
            }

            val intent = Intent(this@Card_change, MainActivity::class.java)
            startActivity(intent)
        }

    }

}
