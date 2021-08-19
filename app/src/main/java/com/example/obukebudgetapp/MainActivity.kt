package com.example.obukebudgetapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(bottomNavListener)
    }

    val bottomNavListener = NavigationBarView.OnItemSelectedListener {
        lateinit var fragment: Fragment
        when (it.itemId) {
            R.id.testMenuItem1 -> {
                fragment = TestFragmentOne()
            }
            R.id.testMenuItem2 -> {
                fragment = TestFragmentTwo()
            }
            R.id.profileMenuOption -> {
                fragment = ProfileFragment()
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        true
    }


}