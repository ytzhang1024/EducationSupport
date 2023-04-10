//package com.android.example.educationsupport.activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.android.example.educationsupport.R
//import com.android.example.educationsupport.databinding.ActivityStudentHomeBinding
//
//class StudentHomeActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityStudentHomeBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_student_home)
//        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        //Jump to AllCoursesActivity
//        binding.allBtn2.setOnClickListener {
//            val intent = Intent(this, AllCoursesActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}
package com.android.example.educationsupport.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityStudentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class StudentHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
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