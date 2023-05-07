package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.data.model.User
import com.android.example.educationsupport.databinding.CourseItemBinding
import com.android.example.educationsupport.databinding.StudentListItemBinding

class StudentListAdapter(
    val onItemClick: (Int, User) -> Unit
) : RecyclerView.Adapter<StudentListAdapter.MyViewHolder>() {

    private var list: MutableList<User> = arrayListOf()

    inner class MyViewHolder(val binding: StudentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User){
            binding.studentEmail.setText(item.email)
//            binding..setText(item.name)
//            binding.courseDescription.setText(item.description)
//            binding.courseTutor.setText(item.tutorEmail)
            binding.itemLayout.setOnClickListener { onItemClick.invoke(adapterPosition,item) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = StudentListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<User>){
        this.list = list
        notifyDataSetChanged()
    }


}