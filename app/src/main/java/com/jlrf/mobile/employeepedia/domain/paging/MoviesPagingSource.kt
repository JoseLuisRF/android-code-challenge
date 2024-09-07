package com.jlrf.mobile.employeepedia.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jlrf.mobile.employeepedia.data.mappers.MovieDataMapper
import com.jlrf.mobile.employeepedia.data.remote.MoviesCloudSource
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import javax.inject.Inject

internal class MoviesPagingSource @Inject constructor(
    private val cloudSource: MoviesCloudSource,
    private val mapper: MovieDataMapper,
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val currentPage = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = cloudSource.getPopularMovies(
                mapper.createPopularMoviesRequest(
                    page = currentPage
                )
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