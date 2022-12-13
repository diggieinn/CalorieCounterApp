package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Info : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)



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
        }




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


}
