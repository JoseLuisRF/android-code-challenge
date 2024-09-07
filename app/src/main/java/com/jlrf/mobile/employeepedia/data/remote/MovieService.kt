package com.jlrf.mobile.employeepedia.data.remote

import com.jlrf.mobile.employeepedia.data.remote.model.GetMovieDetailsResponse
import com.jlrf.mobile.employeepedia.data.remote.model.GetMovieReviewsResponse
import com.jlrf.mobile.employeepedia.data.remote.model.GetPopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular")
    suspend fun getPopularMoviesResponse(
        @HeaderMap headers: Map<String, String>,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
    ): Response<GetPopularMoviesResponse>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("movieId") movieId: Int
    ): Response<GetMovieDetailsResponse>

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @HeaderMap headers: Map<String, String>,
        @Path("movieId") movieId: Int
    ): Response<GetMovieReviewsResponse>

}