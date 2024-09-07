package com.jlrf.mobile.employeepedia.data.remote.model

data class GetPopularMoviesRequest(
    val page: Int = 1,
    val language: String = "en-US",
    val includeAdult: Boolean = false,
    val includeVideo: Boolean = false,
)