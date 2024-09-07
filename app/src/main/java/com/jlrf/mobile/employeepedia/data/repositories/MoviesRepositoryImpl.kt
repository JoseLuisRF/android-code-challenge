package com.jlrf.mobile.employeepedia.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.MoviesCloudSource
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import com.jlrf.mobile.employeepedia.domain.paging.MovieReviewsPagingSource
import com.jlrf.mobile.employeepedia.domain.paging.MoviesPagingSource
import com.jlrf.mobile.employeepedia.domain.repositories.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl @Inject constructor(
    private val cloudDataSource: MoviesCloudSource,
    private val mapper: MovieDataMapper
) : MoviesRepository {

    override suspend fun getPopularMovies(
        page: Int,
        pageSize: Int
    ): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                MoviesPagingSource(
                    cloudDataSource,
                    mapper
                )
            }
        ).flow
    }

    override suspend fun getMovieReviews(
        movieId: Int,
        page: Int,
        pageSize: Int
    ): Flow<PagingData<MovieReviewModel>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                MovieReviewsPagingSource(
                    cloudSource = cloudDataSource,
                    movieId = movieId,
                )
            }
        ).flow
    }
}