package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityItemBinding
import com.android.example.educationsupport.databinding.QuestionDetailItemBinding

//studentQuizDetailAdapter

class QuizDetailAdapter(
    val onItemClick: (Int, Question) -> Unit
) : RecyclerView.Adapter<QuizDetailAdapter.MyViewHolder>() {

    private var list: MutableList<Question> = arrayListOf()


    inner class MyViewHolder(val binding: QuestionDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question){
            binding.question.setText(item.title)
            binding.optionA.setText("A:"+item.option_A)
            binding.optionB.setText("B:"+item.option_B)
            binding.optionC.setText("C:"+item.option_C)
            binding.optionD.setText("D:"+item.option_D)
            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }
        fun getSelectedOptions(): List<String>? {
            var res: List<String>? = null

            val checkBoxA = binding.checkBoxA
            val checkBoxB = binding.checkBoxB
            val checkBoxC = binding.checkBoxC
            val checkBoxD = binding.checkBoxD

            if (checkBoxA.isChecked) {
                res = res.orEmpty().plus("A")
                println("click checkBoxA:"+ binding.optionA)
            }
            if (checkBoxB.isChecked) {
                res = res.orEmpty().plus("B")
            }
            if (checkBoxC.isChecked) {
                res = res.orEmpty().plus("C")
            }
            if (checkBoxD.isChecked) {
                res = res.orEmpty().plus("D")
            }


            return res
        }

fun setCheckBox(): List<String>? {
    val selectedOptions = mutableListOf<String>()
    binding.checkBoxA.isChecked = false
    binding.checkBoxB.isChecked = false
    binding.checkBoxC.isChecked = false
    binding.checkBoxD.isChecked = false

    if (binding.checkBoxA.isChecked) {
        selectedOptions.add("A")

    }
    if (binding.checkBoxB.isChecked) {
        selectedOptions.add("B")

    }
    if (binding.checkBoxC.isChecked) {
        selectedOptions.add("C")

    }
    if (binding.checkBoxD.isChecked) {
        selectedOptions.add("D")

    }

    return selectedOptions.takeIf { it.isNotEmpty() }
     }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = QuestionDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Question>){
        this.list = list
        notifyDataSetChanged()
    }






}