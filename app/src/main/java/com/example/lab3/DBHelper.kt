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
        const val KEY_FIRSTNAME = "first_name"
        const val KEY_LASTNAME = "last_name"
        const val KEY_PHONE = "phone"
        const val KEY_BIRTHDAY = "birthday"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
        CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_FIRSTNAME TEXT NOT NULL,
            $KEY_LASTNAME TEXT NOT NULL,
            $KEY_PHONE TEXT NOT NULL,
            $KEY_BIRTHDAY TEXT NOT NULL
        )"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAll(): List<Contact> {
        val result = mutableListOf<Contact>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val fnIndex: Int = cursor.getColumnIndex(KEY_FIRSTNAME)
            val lnIndex: Int = cursor.getColumnIndex(KEY_LASTNAME)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            val birthdayIndex: Int = cursor.getColumnIndex(KEY_BIRTHDAY)
            do {
                val contact = Contact(
                    cursor.getLong(idIndex),
                    cursor.getString(fnIndex),
                    cursor.getString(lnIndex),
                    cursor.getString(phoneIndex),
                    cursor.getString(birthdayIndex),
                )
                result.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    //Добавление записи
    fun add(firstName: String, lastName: String, phone: String, birthday: String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_FIRSTNAME, firstName)
        contentValues.put(KEY_LASTNAME, lastName)
        contentValues.put(KEY_PHONE, phone)
        contentValues.put(KEY_BIRTHDAY, birthday)

        // свежедобавленный ID
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    //Обновление записи
    fun update(
        id: Long,
        lastName: String,
        firstName: String,
        phone: String,
        birthday: String
    ) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_FIRSTNAME, firstName)
/*
        contentValues.put(KEY_FIRSTNAME, firstName)
        contentValues.put(KEY_FIRSTNAME, firstName)
        contentValues.put(KEY_FIRSTNAME, firstName)
        contentValues.put(KEY_FIRSTNAME, firstName)
*/
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }
    fun getById(id: Long): Contact? {
        var result: Contact? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val fnIndex: Int = cursor.getColumnIndex(KEY_FIRSTNAME)
            val lnIndex: Int = cursor.getColumnIndex(KEY_LASTNAME)
            val phoneIndex: Int = cursor.getColumnIndex(KEY_PHONE)
            val birthdayIndex: Int = cursor.getColumnIndex(KEY_BIRTHDAY)
            result = Contact(
                cursor.getLong(idIndex),
                cursor.getString(fnIndex),
                cursor.getString(lnIndex),
                cursor.getString(phoneIndex),
                cursor.getString(birthdayIndex),
            )
        }
        cursor.close()
        return result
    }

}