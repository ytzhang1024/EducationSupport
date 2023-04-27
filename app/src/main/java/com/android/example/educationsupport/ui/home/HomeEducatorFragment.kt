package com.android.example.educationsupport.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.FragmentEducatorHomeBinding
import com.android.example.educationsupport.ui.course.AllCoursesActivity
import com.android.example.educationsupport.ui.course.SelectedActivity
import com.android.example.educationsupport.ui.course.StudentMarkListActivity
import com.android.example.educationsupport.ui.quiz.CreateModuleActivity
import com.android.example.educationsupport.viewModel.HomeViewModel



class HomeEducatorFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEducatorHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentEducatorHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //        Jump to All courses page
        val btnAll: Button = binding.allBtn
        btnAll.setOnClickListener {
            val intent = Intent(activity, AllCoursesActivity::class.java)
            startActivity(intent)
        }

//        Jump to selected courses page
        val btnSelected: Button = binding.selectedBtn
        btnSelected.setOnClickListener {
            val intent = Intent(activity, SelectedActivity::class.java)
            startActivity(intent)
        }

//        Jump to create module page
        val btnCreate: Button = binding.createBtn
        btnCreate.setOnClickListener {
            val intent = Intent(activity, CreateModuleActivity::class.java)
            startActivity(intent)
        }

//        Jump to course result page
        val btnCourseResult: Button = binding.marksBtn
        btnCourseResult.setOnClickListener {
            val intent = Intent(activity, StudentMarkListActivity::class.java)
            startActivity(intent)
        }

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        val btnCourse: Button = binding.btnCourse
//        btnCourse.setOnClickListener() {
////            btnCourse.text = "Hello"
//            println("click")
//            val intent = Intent(activity, Module::class.java)
//            startActivity(intent)
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
//        val btnCourse: Button = binding.btnCourse
//        btnCourse.setOnClickListener() {
//            btnCourse.text = "Hello"
//            println("click")
//            val intent = Intent(activity, Module::class.java)
//            startActivity(intent)
//        }
    }
}