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
private val COL_PROTEIN = "protein"
private val COL_CARBS = "carbs"
private val COL_FAT = "fat"
private val COL_DATE = "date"
private val COL_CATEGORY = "category"


class MealDatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_MEALENAME + " VARCHAR(256)," +
                COL_CALORIES + " INTEGER," +
                COL_PROTEIN + " INTEGER," +
                COL_CARBS + " INTEGER," +
                COL_FAT + " INTEGER," +
                COL_DATE + " VARCHAR(256)," +
                COL_CATEGORY + " VARCHAR(256))"


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
        cv.put(COL_PROTEIN, meal.protein)
        cv.put(COL_CARBS, meal.carbs)
        cv.put(COL_FAT, meal.fat)
        cv.put(COL_DATE, meal.date)
        cv.put(COL_CATEGORY, meal.category)
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
              meal.protein = result.getString(result.getColumnIndex(COL_PROTEIN)).toInt()
              meal.carbs = result.getString(result.getColumnIndex(COL_CARBS)).toInt()
              meal.fat = result.getString(result.getColumnIndex(COL_FAT)).toInt()
              meal.date = result.getString(result.getColumnIndex(COL_DATE))
              meal.category = result.getString(result.getColumnIndex(COL_CATEGORY))


              list.add(meal)
          }while(result.moveToNext())
      }
      result.close()
      db.close()
      return list
  }


    // Delete data all data from the database but not the database itself
    fun deleteAllData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}