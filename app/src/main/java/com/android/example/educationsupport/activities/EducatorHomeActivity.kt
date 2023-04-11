//package com.android.example.educationsupport.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.android.example.educationsupport.R
//import com.android.example.educationsupport.databinding.ActivityEducatorHomeBinding
//
//class EducatorHomeActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityEducatorHomeBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_educator_home)
//        binding = ActivityEducatorHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        //Jump to AllCoursesActivity
//        binding.allBtn.setOnClickListener {
//            val intent = Intent(this, AllCoursesActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//}
package com.android.example.educationsupport.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityEducatorHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class EducatorHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEducatorHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEducatorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_educator)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}