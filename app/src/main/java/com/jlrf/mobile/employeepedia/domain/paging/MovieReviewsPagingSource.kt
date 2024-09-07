package com.jlrf.mobile.employeepedia.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.MoviesCloudSource
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import javax.inject.Inject

class MovieReviewsPagingSource @Inject constructor(
    private val cloudSource: MoviesCloudSource,
    private val movieId: Int,
) : PagingSource<Int, MovieReviewModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieReviewModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReviewModel> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = cloudSource.getMovieReviews(
                movieId = movieId,
                page = currentPage
            )

            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isNullOrEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}