package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.R
import com.android.example.educationsupport.repository.entity.Course

class CourseAdapter(private val courseList:ArrayList<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    class CourseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val courseName: TextView = itemView.findViewById(R.id.courseName)
        val tutor: TextView = itemView.findViewById(R.id.tutor)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.courselist_item, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.courseName.text = courseList[position].name
        holder.tutor.text = courseList[position].tutor
        holder.description.text = courseList[position].description
    }
}