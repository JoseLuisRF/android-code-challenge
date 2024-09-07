package com.jlrf.mobile.employeepedia.domain.repositories

import androidx.paging.PagingData
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(
        page: Int,
        pageSize: Int
    ): Flow<PagingData<MovieModel>>

    suspend fun getEmployeeDetails(id: Long): EmployeeModel?
}