package com.jlrf.mobile.employeepedia.domain.repositories

import androidx.paging.PagingData
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(
        page: Int,
        pageSize: Int
    ): Flow<PagingData<MovieModel>>

    suspend fun getMovieReviews(
        movieId: Int,
        page: Int,
        pageSize: Int
    ): Flow<PagingData<MovieReviewModel>>
}