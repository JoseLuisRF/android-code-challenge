package com.jlrf.mobile.employeepedia.domain.usecases

import androidx.paging.PagingData
import arrow.core.Either
import com.jlrf.mobile.employeepedia.domain.base.UseCase
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import com.jlrf.mobile.employeepedia.domain.repositories.MoviesRepository
import com.jlrf.mobile.employeepedia.util.extensions.toError
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MoviesRepository
) : UseCase<Flow<PagingData<MovieReviewModel>>, GetMovieReviewsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Error, Flow<PagingData<MovieReviewModel>>> {
        return try {
            val response = repository.getMovieReviews(
                movieId = params.movieId,
                page = params.page,
                pageSize = params.pageSize
            )
            Either.Right(response)
        } catch (throwable: Throwable) {
            Either.Left(throwable.toError())
        }
    }

    data class Params(
        val movieId: Int,
        val page: Int = 1,
        val pageSize: Int = 20,
    )
}