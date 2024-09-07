package com.jlrf.mobile.employeepedia.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetMovieReviewsResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<ReviewResult>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
)

data class ReviewResult(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)

data class AuthorDetails(
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("avatar_path") val avatarPath: String? = null,
    @SerializedName("rating") val rating: Double? = null
)
