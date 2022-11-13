package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class targetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)



        //button to go to everydayActivity
        val everydayButton = findViewById<Button>(R.id.buttonToEveryday)
        everydayButton.setOnClickListener {
            val intent = Intent(this, everydayActivity::class.java)
            startActivity(intent)
        } //end of everydayButton

    }
}