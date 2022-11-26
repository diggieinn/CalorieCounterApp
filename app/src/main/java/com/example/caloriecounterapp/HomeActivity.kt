package com.example.caloriecounterapp


import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.caloriecounterapp.R
import com.example.caloriecounterapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            val drawerLayout : androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawerLayout)
            toggle = ActionBarDrawerToggle(this@HomeActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.person -> {
                        Toast.makeText(this@HomeActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.menu_list -> {
                        Toast.makeText(this@HomeActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.info -> {
                        Toast.makeText(this@HomeActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}