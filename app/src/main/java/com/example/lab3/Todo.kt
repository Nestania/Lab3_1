package com.example.lab3

import android.content.ContentValues
import android.database.Cursor
import android.system.Os.close
import com.example.lab3.DBHelper.Companion.KEY_ID
import com.example.lab3.DBHelper.Companion.KEY_IS_DONE
import com.example.lab3.DBHelper.Companion.KEY_TITLE
import com.example.lab3.DBHelper.Companion.TABLE_NAME

data class Todo(
    val id: Long,
    val title: String,
    val isDone: Boolean = false
)

