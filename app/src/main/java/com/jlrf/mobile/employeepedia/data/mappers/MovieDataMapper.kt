package com.jlrf.mobile.employeepedia.data.mappers

import com.jlrf.mobile.employeepedia.data.remote.model.GetMovieReviewsResponse
import com.jlrf.mobile.employeepedia.data.remote.model.GetPopularMoviesRequest
import com.jlrf.mobile.employeepedia.data.remote.model.MovieDto
import com.jlrf.mobile.employeepedia.domain.models.MovieGenre
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReview
import javax.inject.Inject

class MovieDataMapper @Inject constructor() {

    fun createPopularMoviesRequest(
        page: Int
    ): GetPopularMoviesRequest {
        return GetPopularMoviesRequest(
            page = page
        )
    }

    fun convertToModel(dto: MovieDto): MovieModel {
        return MovieModel(
            genreIds = dto.genreIds?.map { MovieGenre(id = it) } ?: emptyList(),
            id = dto.id ?: -1,
            overview = dto.overview.orEmpty(),
            popularity = dto.popularity ?: -1.0,
            posterPath = dto.posterPath?.takeIf { it.isNotBlank() }
                ?.let { "https://image.tmdb.org/t/p/w500$it" }.orEmpty(),
            releaseDate = dto.releaseDate.orEmpty(),
            title = dto.title.orEmpty(),
            originalTitle = dto.originalTitle.orEmpty(),
            originalLanguage = dto.originalLanguage.orEmpty(),
        )
    }

    fun convertToModel(
        movieId: Int,
        reviewsDto: GetMovieReviewsResponse
    ): List<MovieReview>? {
        return reviewsDto.results?.map { reviewDto ->
            MovieReview(
                id = reviewDto.id.orEmpty(),
                movieId = movieId,
                author = reviewDto.author.orEmpty(),
                content = reviewDto.content.orEmpty(),
                avatarPath = reviewDto.authorDetails?.avatarPath?.takeIf { it.isNotBlank() }
                    ?.let { "https://image.tmdb.org/t/p/w500$it" }.orEmpty(),
                rating = reviewDto.authorDetails?.rating ?: 0.0,
                createdAt = reviewDto.createdAt.orEmpty(),
                updatedAt = reviewDto.updatedAt.orEmpty(),
            )
        }
    }
}