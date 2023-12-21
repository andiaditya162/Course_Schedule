package com.dityapra.courseschedule.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dityapra.courseschedule.util.QueryType
import com.dityapra.courseschedule.util.QueryUtil.nearestQuery
import com.dityapra.courseschedule.util.QueryUtil.sortedQuery
import com.dityapra.courseschedule.util.SortType
import com.dityapra.courseschedule.util.executeThread
import java.util.Calendar
import java.util.GregorianCalendar

class DataRepository(private val dao: CourseDao) {
    fun getNearestSchedule(queryType: QueryType) : LiveData<Course?> {
        val query = nearestQuery(queryType)
        return dao.getNearestSchedule(query)
    }

    fun getAllCourse(sortType: SortType): LiveData<PagedList<Course>> {
        val query = sortedQuery(sortType)
        val course = dao.getAll(query)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(course, config).build()
    }

    fun getCourse(id: Int) : LiveData<Course> {
        return dao.getCourse(id)
    }

    fun getTodaySchedule() : List<Course> {
        var day = GregorianCalendar().get(Calendar.DAY_OF_WEEK) - 1
        if (day == 0) day = 7
        return dao.getTodaySchedule(day)
    }

    fun insert(course: Course) = executeThread {
        dao.insert(course)
    }

    fun delete(course: Course) = executeThread {
        dao.delete(course)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        private const val PAGE_SIZE = 10
        fun getInstance(context: Context): DataRepository? {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = CourseDatabase.getInstance(context)
                    instance = DataRepository(database.courseDao())
                }
                return instance
            }
        }
    }
}