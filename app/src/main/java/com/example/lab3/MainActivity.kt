package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<Contact>()
    private lateinit var adapter: RecyclerAdapter
    val dbHelper = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(getString(R.string.app_name), "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val list = mutableListOf<Todo>()
        list.addAll(dbHelper.getAll())
        println(list.joinToString(", "))
        adapter = RecyclerAdapter(list) {
            // адаптеру передали обработчик удаления элемента
            val id = list[it].id
            val intent = Intent(this@MainActivity, Card_contact::class.java )
            intent.putExtra("Id",id)
            startActivity(intent)

            //  dbHelper.remove(list[it].id)
            //  list.removeAt(it)
            //  adapter.notifyItemRemoved(it)
        }





        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
// у нас будет линейный список
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editTextPhone)
        val buttonAdd = findViewById<Button>(R.id.button)
// реакция на нажатие
        buttonAdd.setOnClickListener {
            val intent = Intent(this, Card_change::class.java)
            startActivity(intent)
        }
    }



}