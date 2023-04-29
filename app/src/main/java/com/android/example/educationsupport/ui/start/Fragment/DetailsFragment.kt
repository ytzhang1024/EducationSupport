package com.android.example.educationsupport.ui.start.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.android.example.educationsupport.R
import com.android.example.educationsupport.databinding.FragmentDetailsBinding
import com.android.example.educationsupport.ui.start.activity.QuizViewModel
import com.android.example.educationsupport.utils.show
import dagger.hilt.android.AndroidEntryPoint


//Fragment难用得要死，狗都不用

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: QuizViewModel by viewModels()
    lateinit var activityName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityName = arguments?.getString("activityName").toString()

        val params = bundleOf(
//            "activityName" to activityName,
            )

        println("----------------0--------------------------")
        println(activityName)
//
//        if (activityName != null) {
//            viewModel.checkStudentIfFinishTest(activityName)
//        }

        observer()

//        println("----------------1--------------------------")
        binding.startBtn.setOnClickListener {
            var navController = view?.findNavController()
            navController?.navigate(R.id.quizFragment, params)
        }
    }

    private fun observer(){
//        println("-------------------2-------------------------")
        viewModel.isFinish.observe(viewLifecycleOwner) { state ->
//            println("--------------3----------------------------")
            when (state) {
                true -> binding.startBtn.show()
                else -> {
                    binding.resultBtn.show()
                }
            }
        }
    }
}
