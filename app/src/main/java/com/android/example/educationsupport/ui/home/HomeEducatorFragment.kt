package com.android.example.educationsupport.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.FragmentEducatorHomeBinding
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