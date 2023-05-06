package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityItemBinding
import com.android.example.educationsupport.databinding.QuestionDetailItemBinding
import com.android.example.educationsupport.databinding.TutorQuestionDetailItemBinding

//studentQuizDetailAdapter

class TutorQuizDetailAdapter(
    val onItemClick: (Int, Question) -> Unit
) : RecyclerView.Adapter<TutorQuizDetailAdapter.MyViewHolder>() {

    private var list: MutableList<Question> = arrayListOf()


    inner class MyViewHolder(val binding: TutorQuestionDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question){
            binding.question.setText(item.title)
            binding.optionA.setText("A:"+item.option_A)
            binding.optionB.setText("B:"+item.option_B)
            binding.optionC.setText("C:"+item.option_C)
            binding.optionD.setText("D:"+item.option_D)
            binding.correctAnswer.setText("correct answer:"+item.correct_answer)

            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorQuizDetailAdapter.MyViewHolder {
        val itemView = TutorQuestionDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TutorQuizDetailAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Question>){
        this.list = list
        notifyDataSetChanged()
    }









}