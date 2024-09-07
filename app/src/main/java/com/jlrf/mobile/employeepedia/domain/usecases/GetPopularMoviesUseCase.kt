package com.jlrf.mobile.employeepedia.domain.usecases

import androidx.paging.PagingData
import arrow.core.Either
import com.jlrf.mobile.employeepedia.domain.base.UseCase
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.repositories.MoviesRepository
import com.jlrf.mobile.employeepedia.util.extensions.toError
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) : UseCase<Flow<PagingData<MovieModel>>, GetPopularMoviesUseCase.Params>() {

    override suspend fun run(params: Params): Either<Error, Flow<PagingData<MovieModel>>> {
        return try {
            val response = repository.getPopularMovies(
                page = params.page,
                pageSize = params.pageSize
            )
            Either.Right(response)
        } catch (throwable: Throwable) {
            Either.Left(throwable.toError())
        }
    }

    data class Params(val page: Int = 1, val pageSize: Int = 20)
}