package com.example.caloriecounterapp


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

private val DATABASE_NAME = "Meals.db"
private val TABLE_NAME = "meals"
private val COL_MEALENAME = "mealName"
private val COL_CALORIES = "calories"
private val COL_ID = "id"

class MealDatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_MEALENAME + " VARCHAR(256)," +
                COL_CALORIES + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertMeal(meal: Meals){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_MEALENAME, meal.name)
        cv.put(COL_CALORIES, meal.calories)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

  @SuppressLint("Range")
  fun readData(): MutableList<Meals>{
      val list: MutableList<Meals> = ArrayList()

      val db = this.readableDatabase
      val query = "Select * from " + TABLE_NAME
      val result = db.rawQuery(query, null)

      if(result.moveToFirst()){
          do{
              val meal = Meals()
              meal.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
              meal.name = result.getString(result.getColumnIndex(COL_MEALENAME))
              meal.calories = result.getString(result.getColumnIndex(COL_CALORIES)).toInt()


              list.add(meal)
          }while(result.moveToNext())
      }





      result.close()
      db.close()
      return list
  }


}
