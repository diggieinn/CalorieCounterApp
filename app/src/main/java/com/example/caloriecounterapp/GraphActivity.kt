package com.example.caloriecounterapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
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

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView




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

        // variable to get the current date and send to string format mm/dd/yyyy

        val day = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())


        var db = DatabaseHandler(this)
        var user = db.readData()

        var dbMeal = MealDatabaseHandler(this)
        val data = dbMeal.readDataByDate(day)




        text_date = findViewById<TextView>(R.id.dateText)
        button_date = findViewById<Button>(R.id.changeDateOnGraph)



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


                    // set text to the textviews as percentages of calori

                    var totalCalories = user[0].caloriesTarget
                    var remainingCalories = 0
                    for (i in 0 until data.size) {
                        remainingCalories += data.get(i).calories
                    }



                    Toast.makeText(this@GraphActivity, "Carbs: $calories, Protein: $protein, Fat: $fat Calories: $calories", Toast.LENGTH_LONG).show()

                    var c = (calories.toDouble()/totalCalories)*100
                    var p = (protein.toDouble()/120)*100
                    var f = (fat.toDouble()/65)*100
                    var ca = (carbs.toDouble()/300)*100

                    CaloriesTextView!!.text = c.toString() + " cal"
                    ProteinTextView!!.text = p.toString() + " g"
                    FatTextView!!.text = f.toString() + " g"
                    CarbsTextView!!.text = c.toString() + " g"

                    CarbsProgressBar!!.progress = c.toInt()
                    ProteinProgressBar!!.progress = p.toInt()
                    FatProgressBar!!.progress = f.toInt()
                    CaloriesProgressBar!!.progress = ca.toInt()


                }
            }
        )

        //navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout)

        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarToggle.syncState()


        navView = findViewById(R.id.navView)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.mealManager -> {

                    Intent(this, everydayActivity::class.java).also {
                        startActivity(it)
                    }

                    Toast.makeText(this, "Meal Manager", Toast.LENGTH_SHORT).show()
                    true

                }

                R.id.person -> {

                    Intent(this, Profile::class.java).also {
                        startActivity(it)
                    }

                    Toast.makeText(this, "Person", Toast.LENGTH_SHORT).show()
                    true

                }
                R.id.menu_list -> {

                    Intent(this, MealList::class.java).also {
                        startActivity(it)
                    }


                    Toast.makeText(this, "Menu List", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.info -> {


                    Intent(this, Info::class.java).also {
                        startActivity(it)
                    }


                    Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_graph -> {


                    Intent(this, GraphActivity::class.java).also {
                        startActivity(it)
                    }


                    Toast.makeText(this, "Schedule Water Intake", Toast.LENGTH_SHORT).show()
                    true
                }


                R.id.scheduleWater -> {


                    Intent(this, DrinkWaterActivity::class.java).also {
                        startActivity(it)
                    }


                    Toast.makeText(this, "Schedule Water Intake", Toast.LENGTH_SHORT).show()
                    true
                }


                else -> {
                    false
                }
            }
        }//navigation drawer


        // get the date from the textview and data from database to display on progress bar










    }



    fun updateDate() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_date!!.text = sdf.format(cal.getTime())
    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}

