package com.example.caloriecounterapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class DrinkWaterActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_water)

        //drawer

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


                    Intent(this, Info::class.java).also {
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





        var scheduleWaterIntakeBtn = findViewById<Button>(R.id.scheduleWaterBtn)
        scheduleWaterIntakeBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var datePicker = findViewById<DatePicker>(R.id.datePickerWater)
                var timePicker = findViewById<TimePicker>(R.id.timePickerWater)
                var day = datePicker.dayOfMonth
                var month = datePicker.month
                var year = datePicker.year
                var hour = timePicker.hour
                var minute = timePicker.minute
                var calendar = Calendar.getInstance()
                calendar.set(year, month, day, hour, minute, 0)
                var time = calendar.timeInMillis


                triggerNotificationAtTime(time)
            }
        })
    }






    //triggerNotification() at a specific time
    fun triggerNotificationAtTime(time : Long){
        var notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("MY_CHANNEL_ID", "My Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            // Configure the notification channel.
            notificationChannel.description = " "
            notificationChannel.vibrationPattern = longArrayOf(0, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        var nb : NotificationCompat.Builder = NotificationCompat.Builder(this, "MY_CHANNEL_ID")
        nb.setDefaults(Notification.DEFAULT_ALL)
        nb.setWhen(System.currentTimeMillis())
        nb.setSmallIcon(R.mipmap.ic_launcher)
        nb.setTicker(" ")
        nb.setContentTitle(" ")
        nb.setContentText(" ")
        nb.setContentInfo(" ");


        // create an intent to trigger the notification
        var intent : Intent = Intent(this, NotificationHandler::class.java)
        var pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        // create an alarm manager to trigger the notification
        var alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)

        var toast = Toast.makeText(this, "Notification set", Toast.LENGTH_SHORT)

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


//}