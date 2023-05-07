package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.data.model.QuizRecord
import com.android.example.educationsupport.databinding.ActivityItemBinding
import com.android.example.educationsupport.databinding.QuestionDetailItemBinding
import com.android.example.educationsupport.databinding.StudentReviewItemBinding
import com.android.example.educationsupport.databinding.TutorQuestionDetailItemBinding

//studentQuizDetailAdapter

class StudentReviewlAdapter(
    val onItemClick: (Int, QuizRecord) -> Unit
) : RecyclerView.Adapter<StudentReviewlAdapter.MyViewHolder>() {

    private var list: MutableList<QuizRecord> = arrayListOf()


    inner class MyViewHolder(val binding: StudentReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuizRecord){

            binding.activityName.setText("Activity:"+item.activity_name)

            binding.studentEmail.setText("Email:"+item.student_email)

            binding.studentAnswer.setText("student answer:"+item.answer)

            binding.correctAnswer.setText("correct answer:"+item.correct_answer.toString())

            binding.mark.setText("mark:"+item.mark)

            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentReviewlAdapter.MyViewHolder {
        val itemView = StudentReviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StudentReviewlAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<QuizRecord>){
        this.list = list
        notifyDataSetChanged()
    }









}