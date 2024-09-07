package com.jlrf.mobile.employeepedia.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetPopularMoviesResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    @SerializedName("results")
    val results: List<MovieDto>? = null
)