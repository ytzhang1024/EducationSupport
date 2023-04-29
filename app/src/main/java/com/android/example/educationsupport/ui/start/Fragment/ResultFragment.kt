package com.android.example.educationsupport.ui.start.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.FragmentResultBinding
import com.android.example.educationsupport.ui.quiz.QuizViewModel

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var quizId: String
    private lateinit var currentUserId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resultsHomeBtn.setOnClickListener {
            val navController = findNavController()
            navController.popBackStack(R.id.detailsFragment, false)
        }


    }
}