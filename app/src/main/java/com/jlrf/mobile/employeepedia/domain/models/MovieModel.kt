package com.jlrf.mobile.employeepedia.domain.models

data class MovieModel(
    val genreIds: List<MovieGenre>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
)

data class MovieGenre(
    val id: Int,
    val name: String = "",
)

data class MovieReview(
    val id: String,
    val movieId: Int,
    val author: String,
    val content: String,
    val avatarPath: String,
    val rating: Double,
    val createdAt: String,
    val updatedAt: String,
)