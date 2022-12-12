package com.example.caloriecounterapp

import android.Manifest
import android.app.DatePickerDialog
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
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.core.view.GravityCompat
import com.example.caloriecounterapp.R

import kotlinx.*
import java.text.SimpleDateFormat
import java.util.*


class everydayActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    var button_date : Button? = null
    var text_date : TextView? = null
    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_everyday)




        loadData()
        calculateCalories()
        calculateCaloriesBurned()
        calculateGoalCalories()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        var context = this
        var db = MealDatabaseHandler(context)


        val btnAddMeal = findViewById<Button>(R.id.btnAddMeal)

        val editFood = findViewById<TextView>(R.id.editFood)
        val editCalories = findViewById<TextView>(R.id.editCalories)
        val editCategory = findViewById<TextView>(R.id.editCategory)
        val editFat = findViewById<TextView>(R.id.editFat)
        val editCarbs = findViewById<TextView>(R.id.editCarbs)
        val editProtein = findViewById<TextView>(R.id.editProtein)
        val editCupOfWater = findViewById<TextView>(R.id.editCupOfWater)

        val buttonCupOfWater = findViewById<Button>(R.id.buttonCupOfWater)


        val btnNewDay = findViewById<Button>(R.id.btnNewDay)



        text_date = findViewById(R.id.textViewDate)
        button_date = findViewById(R.id.btnChangeDate)

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
                    DatePickerDialog(this@everydayActivity,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show(
                )
            }
        })


        //navigation drawer
        // Display the hamburger icon to launch the drawer

        // Call findViewById on the DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()


        // Call findViewById on the NavigationView
        navView = findViewById(R.id.navView)

        // Call setNavigationItemSelectedListener on the NavigationView to detect when items are clicked
        natview()


        addMeal(
            btnAddMeal,
            editFood,
            editCalories,
            editFat,
            editCarbs,
            editProtein,
            editCategory,
            db,
            editCupOfWater
        )

        buttonCupOfWater.setOnClickListener {

            var water = db.readDataByDate(text_date!!.text.toString())
            //get all the water from the database where category is water
            var totalWater = 0
            for (i in water) {
                if (i.category == "Water") {
                    totalWater++
                }
            }


            var meal = Meals("Water", 0, 0, 0, 0, day, "Water",1)
            db.insertMeal(meal)

            editCupOfWater.text = "Cup of water: "+ totalWater.toString()
        }





        btnNewDay.setOnClickListener{
            db.deleteAllData()
            calculateCalories()
            calculateCaloriesBurned()
            resetSteps()
        }

    }

    fun addMeal(
        btnAddMeal: Button,
        editFood: TextView,
        editCalories: TextView,
        editFat: TextView,
        editCarbs: TextView,
        editProtein: TextView,
        editCategory: TextView,
        db: MealDatabaseHandler,
        editCupOfWater: TextView
    ) {
        btnAddMeal.setOnClickListener() {
            if (editFood.text.toString().length > 0 && editCalories.text.toString().length > 0) {
                var meal = Meals(
                    editFood.text.toString(),
                    editCalories.text.toString().toInt(),
                    editFat.text.toString().toInt(),
                    editCarbs.text.toString().toInt(),
                    editProtein.text.toString().toInt(),
                    text_date!!.text.toString(),
                    editCategory.text.toString(),
                    editCupOfWater.text.toString().toInt()
                )
                db.insertMeal(meal)

                calculateCalories()
                calculateCaloriesBurned()
            }
        }
    }

    fun natview() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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

                    Toast.makeText(this, "Menu Graph", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    //fun to reset the steps
    fun resetSteps(){
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        tv_stepsTaken.text = "0"
    }


    private fun loadData(): Float {

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)

        Log.d("Everyday Activity", "$savedNumber")

        previousTotalSteps = savedNumber

        return previousTotalSteps
    }

    override fun onSensorChanged(event: SensorEvent?) {

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

    //function to calculate the calories burned based on steps each step where each step is 0.04 calories
    fun calculateCaloriesBurned() {
        var context = this
        var db = MealDatabaseHandler(context)
        val data = db.readData()

        var dbUser = DatabaseHandler(context)
        val userData = dbUser.readData()

        var totalCalories = 0
        for (i in 0 until data.size) {
            totalCalories += data.get(i).calories
        }
        val caloriesResult = findViewById<TextView>(R.id.txtRemainingCalories)
        caloriesResult.text = "Remaining Calories: " + (totalCalories - loadData() * 0.04.toInt() - userData.get(0).caloriesTarget).toString()
        //("Ramianing Calories should be Goal calories - total calories")
    }
//goal calories calculation
    fun calculateGoalCalories() {
        var context = this


        var dbUser = DatabaseHandler(context)
        val userData = dbUser.readData()


        // BMR if Male = (10 x weight in kg) + (6.25 x height in cm) - (5 x age in years) + 5
        val caloriesResult = findViewById<TextView>(R.id.txtGoalCalories)
        caloriesResult.text = "Goal Calories: " + userData.get(0).caloriesTarget.toString()


    }



    //nav view methods
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

    // date method

    fun updateDate() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        text_date!!.text = sdf.format(cal.getTime())
    }

}