package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.Question
import com.android.example.educationsupport.databinding.ActivityItemBinding


class QuizAdapter(
    val onItemClick: (Int, Question) -> Unit
) : RecyclerView.Adapter<QuizAdapter.MyViewHolder>() {

    private var list: MutableList<Question> = arrayListOf()

    inner class MyViewHolder(val binding: ActivityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question){
            binding.activityName.setText(item.title)


            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ActivityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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