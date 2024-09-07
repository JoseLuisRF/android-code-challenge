package com.jlrf.mobile.employeepedia.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReviewModel
import com.jlrf.mobile.employeepedia.domain.usecases.GetMovieReviewsUseCase
import com.jlrf.mobile.employeepedia.presentation.base.BaseAction
import com.jlrf.mobile.employeepedia.presentation.base.BaseState
import com.jlrf.mobile.employeepedia.presentation.base.BaseViewModel
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
) : BaseViewModel<MovieDetailsViewModel.State, MovieDetailsViewModel.Action>(
    State(),
    dispatcher
) {
    private val _pagingData = MutableStateFlow<PagingData<MovieReviewModel>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<MovieReviewModel>> = _pagingData.asStateFlow()

    fun loadMovieReviews(model: MovieModel) {
        dispatch(Action.MovieDetailsLoaded(movieDetails = model))
        viewModelScope.launch(Dispatchers.Default) {
            getMovieReviewsUseCase.run(
                params = GetMovieReviewsUseCase.Params(
                    movieId = model.id,
                )
            ).fold(
                { error -> handleMovieReviewsError(error) },
                ::handleMovieReviewsSuccess
            )
        }

    }

    override fun reduce(oldState: State, action: Action): State {
        return when (action) {
            is Action.ErrorOccurred -> oldState.copy(
                isLoading = false,
                error = action.error
            )

            is Action.MovieDetailsLoaded -> oldState.copy(
                isLoading = false,
                movie = action.movieDetails
            )

            is Action.Loading -> oldState.copy(
                isLoading = action.value
            )

            is Action.MovieReviewsLoaded -> oldState.copy(
                isLoading = false,
                reviews = action.pagingData
            )
        }
    }

    override fun isLoading(value: Boolean) {
        dispatch(Action.Loading(value))
    }

    private fun handleMovieReviewsError(error: Error) {
        dispatch(Action.ErrorOccurred(error))
    }

    private fun handleMovieReviewsSuccess(reviews: Flow<PagingData<MovieReviewModel>>) {
        viewModelScope.launch(Dispatchers.Default) {
            reviews.cachedIn(viewModelScope).collect {
                // Update PagingData
                _pagingData.value = it

                // Update Screen State
                dispatch(Action.MovieReviewsLoaded(it))
            }
        }
    }


    data class State(
        val movie: MovieModel? = null,
        val reviews: PagingData<MovieReviewModel> = PagingData.empty(),
        val isLoading: Boolean = true,
        val error: Error? = null
    ) : BaseState

    sealed class Action : BaseAction {
        data class Loading(val value: Boolean) : Action()
        data class MovieReviewsLoaded(val pagingData: PagingData<MovieReviewModel>) : Action()
        data class MovieDetailsLoaded(val movieDetails: MovieModel) : Action()
        data class ErrorOccurred(val error: Error) : Action()
    }
}