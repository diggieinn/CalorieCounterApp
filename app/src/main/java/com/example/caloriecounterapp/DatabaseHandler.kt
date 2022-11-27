package com.example.caloriecounterapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


private val DATABASE_NAME = "user.db"
private val TABLE_NAME = "user"
private val COL_NAME = "name"
private val COL_AGE = "age"
private val COL_HEIGHT = "height"
private val COL_WEIGHT = "weight"
private val COL_ID = "id"
private val COL_TARGET_WEIGHT = "targetWeight"
private val COL_WEIGHT_LOSS_TARGET = "weightLossTarget"
private val COL_GENDER = "gender"







class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // create table
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_AGE + " INTEGER," +
                COL_HEIGHT + " INTEGER," +
                COL_WEIGHT + " INTEGER," +
                COL_TARGET_WEIGHT + " INTEGER," +
                COL_WEIGHT_LOSS_TARGET + " INTEGER," +
                COL_GENDER + " VARCHAR(256) )"



        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_AGE, user.age)
        cv.put(COL_HEIGHT, user.height)
        cv.put(COL_WEIGHT, user.weight)
        cv.put(COL_TARGET_WEIGHT, user.targetWeight)
        cv.put(COL_WEIGHT_LOSS_TARGET, user.weightLossTarget)
        cv.put(COL_GENDER, user.gender)

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()


    }

    //fuction to read data from database
    @SuppressLint("Range")
    fun readData(): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                user.height = result.getString(result.getColumnIndex(COL_HEIGHT)).toInt()
                user.weight = result.getString(result.getColumnIndex(COL_WEIGHT)).toInt()
                user.targetWeight = result.getString(result.getColumnIndex(COL_TARGET_WEIGHT)).toInt()
                user.weightLossTarget = result.getString(result.getColumnIndex(COL_WEIGHT_LOSS_TARGET)).toInt()
                user.gender = result.getString(result.getColumnIndex(COL_GENDER))


                list.add(user)
            } while (result.moveToNext())
        }





        result.close()
        db.close()
        return list
    }

    //function that searches if user with ID 1 exists returns true if it does, false if it doesn't
    fun searchUser(): Boolean {
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME + " where " + COL_ID + " = 1"
        val result = db.rawQuery(query, null)

        return result.moveToFirst()
    }



    // Delete data all data from the database but not the database itself
    fun deleteAllData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

}






