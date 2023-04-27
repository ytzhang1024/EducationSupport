package com.android.example.educationsupport.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.educationsupport.R
import com.android.example.educationsupport.data.model.Course

class CourseAdapter(private val courseList:ArrayList<Course>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    inner class CourseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val courseName: TextView = itemView.findViewById(R.id.courseName)
        val tutor: TextView = itemView.findViewById(R.id.tutor)
        val description: TextView = itemView.findViewById(R.id.description)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val pos: Int = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(pos)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
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