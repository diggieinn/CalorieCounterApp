package com.example.caloriecounterapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class GraphActivity : AppCompatActivity() {
    var cal = Calendar.getInstance()
    var button_date : Button? = null
    var text_date : TextView? = null

    var btnUpdate : Button? = null

    var CarbsProgressBar : ProgressBar? = null
    var ProteinProgressBar : ProgressBar? = null
    var FatProgressBar : ProgressBar? = null
    var CaloriesProgressBar : ProgressBar? = null

    var CarbsTextView : TextView? = null
    var ProteinTextView : TextView? = null
    var FatTextView : TextView? = null
    var CaloriesTextView : TextView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        CarbsProgressBar = findViewById<ProgressBar>(R.id.CarbsProgressBar)
        ProteinProgressBar = findViewById<ProgressBar>(R.id.ProteinProgressBar)
        FatProgressBar = findViewById<ProgressBar>(R.id.FatProgressBar)
        CaloriesProgressBar = findViewById<ProgressBar>(R.id.CaloriesProgressBar)
        btnUpdate = findViewById<Button>(R.id.btnUpdateData)

        CarbsTextView = findViewById<TextView>(R.id.idTextCarbsGraph)
        ProteinTextView = findViewById<TextView>(R.id.idTextProteinGraph)
        FatTextView = findViewById<TextView>(R.id.idTextFatGraph)
        CaloriesTextView = findViewById<TextView>(R.id.idTextCaloriesGraph)



        var db = MealDatabaseHandler(this)
        var meals = db.readData()

        text_date = findViewById<TextView>(R.id.dateText)
        button_date = findViewById<Button>(R.id.changeDateOnGraph)

        // variable to get the current date and send to string format mm/dd/yyyy

        val day = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())


        text_date!!.text = day

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()
            }
        }

        button_date!!.setOnClickListener (
            object : View.OnClickListener {
                override  fun onClick(view: View) {
                    DatePickerDialog(this@GraphActivity,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show(
                    )
                }
            }
        )

        btnUpdate!!.setOnClickListener (
            object : View.OnClickListener {
                override  fun onClick(view: View) {
                    var db = MealDatabaseHandler(this@GraphActivity)
                    var meals = db.readDataByDate(text_date!!.text.toString())
                    var carbs = 0
                    var protein = 0
                    var fat = 0
                    var calories = 0
                    for (meal in meals) {
                        if (meal.date == text_date!!.text.toString()) {
                            carbs += meal.carbs
                            protein += meal.protein
                            fat += meal.fat
                            calories += meal.calories
                        }
                    }
                    // a toast to show the data that was updated
                    Toast.makeText(this@GraphActivity, "Carbs: $carbs, Protein: $protein, Fat: $fat, Calories: $calories", Toast.LENGTH_LONG).show()

                    // set text to the textviews as percentages of calories
                    CarbsTextView!!.text = ((carbs * 100)/(calories/0.45)).toString() + "%"
                    ProteinTextView!!.text = ((protein * 100)/(calories/0.45)).toString() + "%"
                    FatTextView!!.text = ((fat * 100)/(calories/0.45)).toString() + "%"
                    CaloriesTextView!!.text = calories.toString() + "kcal"
                    


                    CarbsProgressBar!!.progress = carbs
                    ProteinProgressBar!!.progress = protein
                    FatProgressBar!!.progress = fat
                    CaloriesProgressBar!!.progress = calories
                }
            }
        )



        // get the date from the textview and data from database to display on progress bar










    }



    fun updateDate() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_date!!.text = sdf.format(cal.getTime())
    }



}

