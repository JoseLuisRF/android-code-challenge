package com.jlrf.mobile.employeepedia.data

import androidx.paging.PagingData
import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.MoviesCloudSource
import com.jlrf.mobile.employeepedia.data.repositories.MoviesRepositoryImpl
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import com.jlrf.mobile.employeepedia.domain.repositories.MoviesRepository
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoviesRepositoryTest {
    private val cloudDataSource: MoviesCloudSource = mockk()
    private val mapper: MovieDataMapper = mockk()
    private lateinit var sut: MoviesRepository


    @BeforeEach
    fun setup() {
        sut = MoviesRepositoryImpl(
            cloudDataSource = cloudDataSource,
            mapper = mapper
        )
    }


    @Test
    fun testGetPopularMoviesSuccess(): TestResult = runTest {
        val result = sut.getPopularMovies(
            page = 1,
            pageSize = 20
        )

        assert(result is Flow<PagingData<MovieModel>>)
    }

    @Test
    fun testSuccessGetMovieReviews(): TestResult = runTest {
        val result = sut.getMovieReviews(
            movieId = 1,
            page = 1,
            pageSize = 20
        )

        assert(result is Flow<PagingData<MovieReviewModel>>)
    }
}