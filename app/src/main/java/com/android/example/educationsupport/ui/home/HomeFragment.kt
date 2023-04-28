package com.android.example.educationsupport.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.FragmentHomeBinding
import com.android.example.educationsupport.ui.course.AllCoursesActivity
import com.android.example.educationsupport.ui.course.CourseResultActivity
import com.android.example.educationsupport.ui.course.SelectedCourseActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //Jump to All courses page
        val btnAll: Button = binding.allBtn2
        btnAll.setOnClickListener {
            val intent = Intent(activity, AllCoursesActivity::class.java)
            startActivity(intent)
        }

        //Jump to selected courses page
        val btnSelected : Button = binding.selectedBtn2
        btnSelected.setOnClickListener {
            val intent = Intent(activity, SelectedCourseActivity::class.java)
            startActivity(intent)
        }

        //Jump to course result page
        val btnCourseResult : Button = binding.marksBtn2
        btnCourseResult.setOnClickListener {
            val intent = Intent(activity, CourseResultActivity::class.java)
            startActivity(intent)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {

    }
}