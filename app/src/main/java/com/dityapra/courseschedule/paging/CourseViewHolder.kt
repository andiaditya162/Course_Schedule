package com.dityapra.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dityapra.courseschedule.R
import com.dityapra.courseschedule.data.Course
import com.dityapra.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var course: Course
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        course.apply {
            val dayName = getByNumber(day)
            val timeFormat = String.format(timeString, dayName, startTime, endTime)

            itemView.findViewById<TextView>(R.id.tv_course).text = courseName
            itemView.findViewById<TextView>(R.id.tv_time).text = timeFormat
            itemView.findViewById<TextView>(R.id.tv_lecturer).text = lecturer
        }

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}