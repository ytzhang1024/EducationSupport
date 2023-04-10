package com.android.example.educationsupport.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.FragmentHomeBinding
import com.android.example.educationsupport.Module


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