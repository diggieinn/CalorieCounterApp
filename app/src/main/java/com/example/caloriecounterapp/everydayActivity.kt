package com.example.caloriecounterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.*


class everydayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_everyday)

        var context = this
        var db  = MealDatabaseHandler(context)

        val btnAddMeal = findViewById<Button>(R.id.btnAddMeal)
        val btnShowMeals = findViewById<Button>(R.id.btnShowMeals)
        val editFood  = findViewById<TextView>(R.id.editFood)
        val editCalories = findViewById<TextView>(R.id.editCalories)




        btnAddMeal.setOnClickListener(){
            if(editFood.text.toString().length > 0 && editCalories.text.toString().length > 0){
                var meal = Meals(editFood.text.toString(), editCalories.text.toString().toInt())
                db.insertMeal(meal)
            }
        }


        btnShowMeals.setOnClickListener {
            val data = db.readData()
            val mealsResult = findViewById<TextView>(R.id.mealsResult)
            mealsResult.text = ""
            for (i in 0 until data.size) {
                mealsResult.append("Name of the meal :"+ data.get(i).name + " Calories: " + data.get(i).calories + "\n")


        }
    }
    }}