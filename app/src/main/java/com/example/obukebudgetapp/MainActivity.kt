package com.example.obukebudgetapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(bottomNavListener)
        requestExternalStoragePermissions()
    }

    val bottomNavListener = NavigationBarView.OnItemSelectedListener {
        lateinit var fragment: Fragment
        when (it.itemId) {
            R.id.testMenuItem1 -> {
                fragment = TransactionsFragment()
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

    private val STORAGE_PERMISSIONS_CODE = 123
    private fun requestExternalStoragePermissions(): Boolean {
        val readStoragePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writeStoragePermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val listPermissionsNeeded = ArrayList<String>()

        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }


        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (listPermissionsNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toArray(arrayOfNulls<String>(listPermissionsNeeded.size)),
                STORAGE_PERMISSIONS_CODE
            )
            return false
        }
        return true
    }


}