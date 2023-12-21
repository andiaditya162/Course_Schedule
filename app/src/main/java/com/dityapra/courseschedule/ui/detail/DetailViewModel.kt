package com.dityapra.courseschedule.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dityapra.courseschedule.data.Course
import com.dityapra.courseschedule.data.DataRepository

class DetailViewModel(private val repository: DataRepository, courseId: Int) : ViewModel() {
    val course: LiveData<Course> = repository.getCourse(courseId)

    fun delete() {
        course.value?.let {
            repository.delete(it)
        }
    }
}