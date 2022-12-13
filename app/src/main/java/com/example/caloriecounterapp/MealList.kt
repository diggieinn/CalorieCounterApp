package com.example.caloriecounterapp

import MealAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MealList : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_list_recycled);

        var context = this
        var db = MealDatabaseHandler(context)

        val mealRV = findViewById<RecyclerView>(R.id.idRVCourse)

        var mealModelList = db.readDataByCategory("Water")
        mealModelList.sortBy { it.date }

        val mealAdapter = MealAdapter(context, mealModelList as ArrayList<Meals>)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mealRV.layoutManager = linearLayoutManager
        mealRV.adapter = mealAdapter


        //       val btnShowMeals = findViewById<Button>(R.id.btnShowMeals)

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


    //        btnShowMeals.setOnClickListener {
//            val data = db.readData()
//            val mealsResult = findViewById<TextView>(R.id.mealsResult)
//
//
//            mealsResult.text = ""
//            for (i in 0 until data.size) {
//                if(data.get(i).category == "Water"){
//                    continue;
//                }
//
//                mealsResult.append(
//
//                            "Name: " + data.get(i).name + "" +
//                            "Calories: " + data.get(i).calories + " " +
//                            "Protein: " + data.get(i).protein + "  " +
//                            "Carbs: " + data.get(i).carbs + " " +
//                            "Fat: " + data.get(i).fat + " " +
//                            "Date: " + data.get(i).date + " " +
//                            "Category: " + data.get(i).category + " | "
//                )
//
//
//            }
//        }
//
//
//
//    }
//
////
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
}



