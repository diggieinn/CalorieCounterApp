package com.example.caloriecounterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHandler(this)
        //if user exists in database, go to everydayActivity
        //if user does not exist in database keep going.
        if (db.checkUserExists()) {
            val intent = Intent(this, everydayActivity::class.java)

            startActivity(intent)
        }


        //get information from user and save in txt file of id editTextTextPersonName
        val name = findViewById<EditText>(R.id.editFullName)
        val age = findViewById<EditText>(R.id.editAge)
        val height = findViewById<EditText>(R.id.editHeight)
        val weight = findViewById<EditText>(R.id.editWeight)

        val radioGruop = findViewById<RadioGroup>(R.id.radioGroup)

        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()


        //button to go to targetActivity
        val targetButton = findViewById<Button>(R.id.buttonToTargetActivity)
        targetButton.setOnClickListener {


            if (name.text.toString().length > 0 && (age.text.toString().length > 0 || age.text.toString()
                    .toInt() < 120) && height.text.toString().length > 0 && weight.text.toString().length > 0
            ) {


                //    constructor(name : String, age : Int, height : Int, weight : Int, gender : String, targetWeight : Int, weightLossTarget : Int){

                val nameIn = name.text.toString()
                val ageIn = age.text.toString().toInt()
                val heightIn = height.text.toString().toInt()
                val weightIn = weight.text.toString().toInt()

                var id: Int = radioGruop.checkedRadioButtonId
                val radio: RadioButton = findViewById(id)
                val genderIn = radio.text.toString()


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

        //fun to delete shared preferences infomation


        //go to targetActivity

    } //end of targetButton

    fun radio_button_click(view: View) {
        val radio: RadioButton = findViewById(view.id)
        Toast.makeText(applicationContext, "On button click : ${radio.text}", Toast.LENGTH_SHORT)
            .show()
    }


} //end of onCreate


