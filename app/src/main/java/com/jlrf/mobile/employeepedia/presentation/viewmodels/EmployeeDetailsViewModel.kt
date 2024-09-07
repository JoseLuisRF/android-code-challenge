package com.jlrf.mobile.employeepedia.presentation.viewmodels

import com.jlrf.mobile.employeepedia.domain.GetEmployeeDetailsUseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.models.MovieReview
import com.jlrf.mobile.employeepedia.presentation.base.BaseAction
import com.jlrf.mobile.employeepedia.presentation.base.BaseState
import com.jlrf.mobile.employeepedia.presentation.base.BaseViewModel
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase
) : BaseViewModel<EmployeeDetailsViewModel.State, EmployeeDetailsViewModel.Action>(
    State(),
    dispatcher
) {
    fun loadMovieReviews(model: MovieModel) {
        dispatch(Action.MovieDetailsLoaded(movieDetails = model))

//        viewModelScope.launch(dispatcher.main()) {
//            getEmployeeDetailsUseCase.run(
//                GetEmployeeDetailsUseCase.Params(
//                    id = employeeId
//                )
//            ).fold(
//                { error -> handleEmployeeDetailsError(error) },
//                ::handleEmployeeDetailsSuccess
//            )
//        }
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
                reviews = action.reviews
            )
        }
    }

    override fun isLoading(value: Boolean) {
        dispatch(Action.Loading(value))
    }

    private fun handleEmployeeDetailsError(error: Error) {
        dispatch(Action.ErrorOccurred(error))
    }

    private fun handleEmployeeDetailsSuccess(employeeDetails: EmployeeModel) {
//        dispatch(Action.MovieDetailsLoaded(employeeDetails = employeeDetails))
    }


    data class State(
        val movie: MovieModel? = null,
        val reviews: List<MovieReview> = emptyList(),
        val isLoading: Boolean = true,
        val error: Error? = null
    ) : BaseState

    sealed class Action : BaseAction {
        data class Loading(val value: Boolean) : Action()
        data class MovieReviewsLoaded(val reviews: List<MovieReview>) : Action()
        data class MovieDetailsLoaded(val movieDetails: MovieModel) : Action()
        data class ErrorOccurred(val error: Error) : Action()
    }
}