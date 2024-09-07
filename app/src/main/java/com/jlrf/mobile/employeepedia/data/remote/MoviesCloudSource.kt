package com.jlrf.mobile.employeepedia.data.remote

import android.util.Log
import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.model.GetPopularMoviesRequest
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReview
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import javax.inject.Inject
import kotlinx.coroutines.withContext

class MoviesCloudSource @Inject constructor(
    private val service: MovieService,
    private val mapper: MovieDataMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val serviceSettings: ServiceSettings
) {

    suspend fun getPopularMovies(
        request: GetPopularMoviesRequest
    ): List<MovieModel>? {
        return withContext(dispatcherProvider.io()) {
            val response = service.getPopularMoviesResponse(
                headers = serviceSettings.getAuthorizationHeader(),
                page = request.page,
                language = request.language,
                includeAdult = request.includeAdult,
                includeVideo = request.includeVideo
            )
            response.takeIf { it.isSuccessful && it.body() != null }
                ?.body()?.let { bodyResponse ->
                    bodyResponse.results?.map { dto ->
                        mapper.convertToModel(dto)
                    }
                }
        }
    }

    suspend fun getMovieReviews(movieId: Int): List<MovieReview>? {
        return withContext(dispatcherProvider.io()) {
            try {
                val movieReviewsResponse = service.getMovieReviews(
                    headers = serviceSettings.getAuthorizationHeader(),
                    movieId = movieId
                )
                movieReviewsResponse.takeIf { it.isSuccessful && movieReviewsResponse.body() != null }
                    ?.let {
                        mapper.convertToModel(
                            movieId = movieId,
                            reviewsDto = movieReviewsResponse.body()!!
                        )
                    }
            } catch (ex: Exception) {
                Log.e("JLRF", "exception: ${ex.message}")
                null
            }
        }
    }
}