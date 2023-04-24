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
package com.android.example.educationsupport.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.ActivityEducatorHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.example.educationsupport.ui.course.AllCoursesActivity
import com.android.example.educationsupport.ui.course.CourseResultActivity
import com.android.example.educationsupport.ui.course.SelectedActivity
import com.android.example.educationsupport.ui.course.StudentMarkListActivity
import com.android.example.educationsupport.ui.quiz.CreateModuleActivity

class EducatorHomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEducatorHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEducatorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        Jump to All courses page
        val btnAll = findViewById<Button>(R.id.allBtn)
        btnAll.setOnClickListener {
            val intent = Intent(this, AllCoursesActivity::class.java)
            startActivity(intent)
        }

//        Jump to selected courses page
        val btnSelected = findViewById<Button>(R.id.selectedBtn)
        btnSelected.setOnClickListener {
            val intent = Intent(this, SelectedActivity::class.java)
            startActivity(intent)
        }

//        Jump to create module page
        val btnCreate = findViewById<Button>(R.id.createBtn)
        btnCreate.setOnClickListener {
            val intent = Intent(this, CreateModuleActivity::class.java)
            startActivity(intent)
        }

//        Jump to course result page
        val btnCourseResult = findViewById<Button>(R.id.marksBtn)
        btnCourseResult.setOnClickListener {
            val intent = Intent(this, StudentMarkListActivity::class.java)
            startActivity(intent)
        }

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

    override fun onClick(p0: View?) {
//        when(p0?.id) {
//            R.id.selectedBtn -> {
//                val intent = Intent(this, SelectedActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }
}