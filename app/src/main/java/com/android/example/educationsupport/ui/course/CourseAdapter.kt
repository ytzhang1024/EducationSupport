package com.android.example.educationsupport.ui.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.Course
import com.android.example.educationsupport.databinding.CourseItemBinding

class CourseAdapter(
    val onItemClick: (Int, Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {

    private var list: MutableList<Course> = arrayListOf()

    inner class MyViewHolder(val binding: CourseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course){
            binding.courseName.setText(item.name)
            binding.courseDescription.setText(item.description)
            binding.courseTutor.setText(item.tutorName)
            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = CourseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Course>){
        this.list = list
        notifyDataSetChanged()
    }


}