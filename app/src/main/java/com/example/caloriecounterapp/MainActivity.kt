package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //get information from user and save in txt file of id editTextTextPersonName
        val name = findViewById<EditText>(R.id.editFullName)
        val age = findViewById<EditText>(R.id.editAge)
        val height = findViewById<EditText>(R.id.editHeight)
        val weight = findViewById<EditText>(R.id.editWeight)
        val gender = findViewById<EditText>(R.id.editGender)




        //button to go to targetActivity
        val targetButton = findViewById<Button>(R.id.buttonToTargetActivity)
        targetButton.setOnClickListener {


            if (name.text.toString().length > 0 && age.text.toString().length > 0 && height.text.toString().length > 0 && weight.text.toString().length > 0 && (gender.text.toString().length > 0 || gender.text.toString().length < 2)) {


                //    constructor(name : String, age : Int, height : Int, weight : Int, gender : String, targetWeight : Int, weightLossTarget : Int){

                val nameIn = name.text.toString()
                val ageIn = age.text.toString().toInt()
                val heightIn = height.text.toString().toInt()
                val weightIn = weight.text.toString().toInt()
                val genderIn = gender.text.toString()







                val intent = Intent(this, targetActivity::class.java)

                intent.putExtra("name", nameIn)
                intent.putExtra("age", ageIn)
                intent.putExtra("height", heightIn)
                intent.putExtra("weight", weightIn)
                intent.putExtra("gender", genderIn)




                startActivity(intent)
            } else {
                Toast.makeText(this, "Please Fill all correct data", Toast.LENGTH_SHORT).show()
            }


        }


        //go to targetActivity

    } //end of targetButton



} //end of onCreate


