package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class targetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        var db: DatabaseHandler
        var context = this
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0)
        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)
        val gender = intent.getStringExtra("gender")
        Toast.makeText(
            this,
            "Name: $name, Age: $age, Height: $height, Gender: $gender, Weight: $weight",
            Toast.LENGTH_LONG
        ).show()

        val targetWeight = findViewById<EditText>(R.id.editTargetWeight)
        val weightLossTarget = findViewById<EditText>(R.id.editWeightLossTarget)


        //button to go to everydayActivity
        val everydayButton = findViewById<Button>(R.id.buttonToEveryday)
        everydayButton.setOnClickListener {


            if (targetWeight.text.toString().toInt() > 0 && weightLossTarget.text.toString().toInt() > 0) {


                val targetWeightIn = targetWeight.text.toString().toInt()
                val weightLossTargetIn = weightLossTarget.text.toString().toInt()
                var caloriesTarget = 0

                if(gender.equals("M")){
                    caloriesTarget = ((targetWeightIn * 10) + 6.25 * (height) - 5 * (age) + 5 as Int).toInt()
                }
                else{
                    caloriesTarget = ((targetWeightIn * 10) + 6.25 * (height) - 5 * (age) - 161 as Int).toInt()
                }

                var user = User(
                    name.toString(),
                    age,
                    height,
                    weight,
                    gender.toString(),
                    targetWeightIn,
                    weightLossTargetIn,
                    caloriesTarget

                )

                db = DatabaseHandler(this)
                db.insertData(user)

                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, everydayActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show()
            }

        }

    }
}


