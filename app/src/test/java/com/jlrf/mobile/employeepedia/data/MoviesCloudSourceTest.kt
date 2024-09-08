package com.jlrf.mobile.employeepedia.data

import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.MovieService
import com.jlrf.mobile.employeepedia.data.remote.MoviesCloudSource
import com.jlrf.mobile.employeepedia.data.remote.ServiceSettings
import com.jlrf.mobile.employeepedia.data.remote.model.GetPopularMoviesRequest
import com.jlrf.mobile.employeepedia.data.remote.model.GetPopularMoviesResponse
import com.jlrf.mobile.employeepedia.data.remote.model.MovieDto
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class MoviesCloudSourceTest {

    private val testDispatcherProvider: DispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val service: MovieService = mockk()
    private val mapper: MovieDataMapper = mockk()
    private val serviceSettings: ServiceSettings = mockk()
    private lateinit var sut: MoviesCloudSource

    @BeforeEach
    fun setup() {
        every { serviceSettings.getAuthorizationHeader() } returns mapOf("Authorization" to "Bearer alksjdalksjdalksjdlakjsd")

        sut = MoviesCloudSource(
            service = service,
            mapper = mapper,
            dispatcherProvider = testDispatcherProvider,
            serviceSettings = serviceSettings
        )
    }

    @Test
    fun `given a request when getPopularMovies is called then return a list of movies`(): TestResult =
        runTest {
            val response: Response<GetPopularMoviesResponse> = mockk()
            val mockMovieModel: MovieModel = mockk()
            every { response.isSuccessful } returns true
            every { response.body() } returns GetPopularMoviesResponse(
                page = 1,
                totalPages = 1,
                totalResults = 1,
                results = listOf(
                    MovieDto()
                )
            )

            every { mapper.convertToModel(any()) } returns mockMovieModel
            coEvery { service.getPopularMoviesResponse(any()) } coAnswers {
                response
            }

            val result = sut.getPopularMovies(
                request = GetPopularMoviesRequest()
            )

            verify(exactly = 1) { mapper.convertToModel(any()) }
            coVerify(exactly = 1) { service.getPopularMoviesResponse(any()) }
            verify(exactly = 1) { response.isSuccessful }
            verify(exactly = 2) { response.body() }

            assert(result is List<MovieModel>)
        }

    @Test
    fun `given an error when getPopularMovies is called then return null`(): TestResult =
        runTest {
            val response: Response<GetPopularMoviesResponse> = mockk()

            coEvery { service.getPopularMoviesResponse(any()) } coAnswers { response }
            every { response.isSuccessful } returns false
            every { response.body() } returns GetPopularMoviesResponse(
                page = 1,
                totalPages = 1,
                totalResults = 1,
                results = emptyList()
            )

            val result = sut.getPopularMovies(
                request = GetPopularMoviesRequest()
            )

            verify(exactly = 0) { mapper.convertToModel(any()) }
            coVerify(exactly = 1) { service.getPopularMoviesResponse(any()) }
            verify(exactly = 1) { response.isSuccessful }

            assert(result == null)
        }
}