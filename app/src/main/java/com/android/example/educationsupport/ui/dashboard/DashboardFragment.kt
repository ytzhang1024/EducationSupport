package com.android.example.educationsupport.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.educationsupport.databinding.FragmentDashboardBinding
import com.android.example.educationsupport.data.repository.FirebaseRepository
import com.android.example.educationsupport.ui.base.SignInActivity


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val firebaseRepository = FirebaseRepository()

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

        val btnViewUserCard: ImageButton = binding.card
        btnViewUserCard.setOnClickListener() {
            val intent = Intent(activity, ViewUserActivity::class.java)
            startActivity(intent)
        }


        //点击logout销毁所有的数据
        val btnLogout: ImageButton = binding.logout
        btnLogout.setOnClickListener() {
            if (firebaseRepository.getFirebaseAuth().currentUser != null){
                firebaseRepository.getFirebaseAuth().signOut()
                this.onDestroy()
                val intent = Intent(activity, SignInActivity::class.java)
                startActivity(intent)
            }else{
//                Toast.makeText(this, "You aren't login Yet!", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}