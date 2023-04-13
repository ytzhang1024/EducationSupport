package com.android.example.educationsupport.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.R
import com.android.example.educationsupport.activities.UserProfileActivity
import com.android.example.educationsupport.activities.ViewUserActivity
import com.android.example.educationsupport.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Jump to ViewUserActivity
        val btnProfile: ImageButton = binding.profile
       btnProfile.setOnClickListener() {
            //println("click")
            val intent = Intent(activity, UserProfileActivity::class.java)
            startActivity(intent)
        }
//        val btnProfile = findViewById<ImageButton>(R.id.profile)
//        btnProfile.setOnClickListener {
//            val intent = Intent(this, ViewUserActivity::class.java)
//            startActivity(intent)
//        }

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}