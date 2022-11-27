package com.example.lab3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<Todo>()
    private lateinit var adapter: RecyclerAdapter
    val dbHelper = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(getString(R.string.app_name), "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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




       // val list = mutableListOf<Todo>()
        list.addAll(dbHelper.getAll())

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
// у нас будет линейный список
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editText)
        val buttonAdd = findViewById<Button>(R.id.button)
// реакция на нажатие
        buttonAdd.setOnClickListener {
            // добавляем элемент в список
            val text = editText.text.toString()
            val id = dbHelper.add(text)
            val todo = Todo(
                id,
                text,
                false
            )
            list.add(todo)
            // извещаем адаптер об изменениях
            adapter.notifyItemInserted(list.lastIndex)
        }
    }



}