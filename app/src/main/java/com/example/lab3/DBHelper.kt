package com.example.lab3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Boolean as Boolean1

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // статические константы имеет смысл хранить так:
    companion object {
        // версия БД
        const val DATABASE_VERSION = 1

        // название БД
        const val DATABASE_NAME = "tododb"

        // название таблицы
        const val TABLE_NAME = "todos"

        // названия полей
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_IS_DONE = "is_done"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
        CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_TITLE TEXT NOT NULL,
            $KEY_IS_DONE INTEGER NOT NULL
        )"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAll(): List<Todo> {
        val result = mutableListOf<Todo>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            val isDoneIndex: Int = cursor.getColumnIndex(KEY_IS_DONE)
            do {
                val todo = Todo(
                    cursor.getLong(idIndex),
                    cursor.getString(titleIndex),
                    cursor.getInt(isDoneIndex) == 1
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    //Добавление записи
    fun add(title: String, isDone: kotlin.Boolean = false): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_IS_DONE, if (isDone) 1 else 0)
        // свежедобавленный ID
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    //Обновление записи
    fun update(id: Long, title: String, isDone: Boolean1) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_IS_DONE, if (isDone) 1 else 0)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }
    fun getById(id: Long): Todo? {
        var result: Todo? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            val isDoneIndex: Int = cursor.getColumnIndex(KEY_IS_DONE)
            result = Todo(
                cursor.getLong(idIndex),
                cursor.getString(titleIndex),
                cursor.getInt(isDoneIndex) == 1
            )
        }
        cursor.close()
        return result
    }

}