package com.example.caloriecounterapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import kotlinx.*


class everydayActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps  = 0f
    private var previousTotalSteps = 0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_everyday)

        loadData()
        resetSteps()
        calculateCalories()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager



        var context = this
        var db = MealDatabaseHandler(context)



        val btnAddMeal = findViewById<Button>(R.id.btnAddMeal)
        val btnShowMeals = findViewById<Button>(R.id.btnShowMeals)
        val editFood = findViewById<TextView>(R.id.editFood)
        val editCalories = findViewById<TextView>(R.id.editCalories)





        btnAddMeal.setOnClickListener() {
            if (editFood.text.toString().length > 0 && editCalories.text.toString().length > 0) {
                var meal = Meals(editFood.text.toString(), editCalories.text.toString().toInt())
                db.insertMeal(meal)
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
                calculateCalories()
            }
        }


        btnShowMeals.setOnClickListener {
            val data = db.readData()
            val mealsResult = findViewById<TextView>(R.id.mealsResult)
            mealsResult.text = ""
            for (i in 0 until data.size) {
                mealsResult.append(
                    "Name of the meal :" + data.get(i).name + " Calories: " + data.get(
                        i
                    ).calories + "\n"
                )


            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun resetSteps(){
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        tv_stepsTaken.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tv_stepsTaken.setOnLongClickListener {

            previousTotalSteps = totalSteps

            // When the user will click long tap on the screen,
            // the steps will be reset to 0
            tv_stepsTaken.text = 0.toString()

            // This will save the data
            saveData()

            true
        }

    }

    private fun saveData() {

        // Shared Preferences will allow us to save
        // and retrieve data in the form of key,value pair.
        // In this function we will save data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {

        // In this function we will retrieve data
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)

        // Log.d is used for debugging purposes
        Log.d("MainActivity", "$savedNumber")

        previousTotalSteps = savedNumber
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Calling the TextView that we made in activity_main.xml
        // by the id given to that TextView
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)

        if (running) {
            totalSteps = event!!.values[0]

            // Current steps are calculated by taking the difference of total steps
            // and previous steps
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()

            // It will show the current steps to the user
            tv_stepsTaken.text = ("$currentSteps")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("onAccuracyChanged: Sensor: $sensor; accuracy: $accuracy")
    }

    // function to calculate the calories gained based on meals
    fun calculateCalories() {
        var context = this
        var db = MealDatabaseHandler(context)
        val data = db.readData()
        var totalCalories = 0
        for (i in 0 until data.size) {
            totalCalories += data.get(i).calories
        }
        val caloriesResult = findViewById<TextView>(R.id.txtTotalCalories)
        caloriesResult.text = "Total Calories: " + totalCalories.toString()
    }


}