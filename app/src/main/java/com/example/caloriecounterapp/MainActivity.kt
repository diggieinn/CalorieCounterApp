package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //get information from user and save in txt file of id editTextTextPersonName
        val name = findViewById<EditText>(R.id.editFullName)
        val age = findViewById<EditText>(R.id.editAge)
        val height = findViewById<EditText>(R.id.editHeight)
        val weight = findViewById<EditText>(R.id.editWeight)


        val context = this




        //button to go to targetActivity
        val targetButton = findViewById<Button>(R.id.buttonToTargetActivity)
        targetButton.setOnClickListener {


            if(name.text.toString().length > 0 && age.text.toString().length > 0 && height.text.toString().length > 0 && weight.text.toString().length > 0){

                var user = User(name.text.toString(), age.text.toString().toInt(), height.text.toString().toInt(), weight.text.toString().toInt())
                var db = DatabaseHandler(context)
                db.insertData(user)

             Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "Please Fill all correct data", Toast.LENGTH_SHORT).show()
            }


//            val nameText = name.text.toString()
//            val ageText = age.text.toString()
//            val heightText = height.text.toString()
//            val weightText = weight.text.toString()
//
//            //validate user input
//            if (nameText.isEmpty() || ageText.isEmpty() || heightText.isEmpty() || weightText.isEmpty()) {
//                //if any of the fields are empty, display error message
//                name.error = "Please enter your name"
//                age.error = "Please enter your age"
//                height.error = "Please enter your height"
//                weight.error = "Please enter your weight"
//            } else {
                //if all fields are filled, save to file

//                val file = File(filesDir, "user.txt")
//                file.writeText("$nameText,$ageText,$heightText,$weightText")


            }



                //go to targetActivity

            } //end of targetButton


        }


