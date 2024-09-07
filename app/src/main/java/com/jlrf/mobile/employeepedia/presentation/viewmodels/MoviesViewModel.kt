package com.jlrf.mobile.employeepedia.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jlrf.mobile.employeepedia.domain.models.MovieModel
import com.jlrf.mobile.employeepedia.domain.usecases.GetPopularMoviesUseCase
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
class MoviesViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
) : BaseViewModel<MoviesViewModel.State, MoviesViewModel.Action>(
    State(),
    dispatcher
) {

    private val _pagingData = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<MovieModel>> = _pagingData.asStateFlow()

    override fun reduce(oldState: State, action: Action): State {
        return when (action) {
            is Action.MoviesLoaded -> oldState.copy(
                isLoading = false,
                selectedMovie = null,
                pagingData = action.pagingData
            )

            is Action.ErrorOccurred -> oldState.copy(
                isLoading = false,
                error = action.error
            )

            is Action.Loading -> oldState.copy(
                isLoading = action.value
            )

            is Action.SelectMovie -> oldState.copy(
                selectedMovie = action.model
            )
        }
    }

    public fun selectMovie(movieModel: MovieModel) {
        dispatch(Action.SelectMovie(movieModel))
    }

    public suspend fun loadEmployees() {
        isLoading(true)
        getPopularMoviesUseCase.run(
            params = GetPopularMoviesUseCase.Params()
        ).fold(
            { error -> handleEmployeesError(error) },
            ::handleEmployeesSuccess
        )
    }

    override fun isLoading(value: Boolean) {
        dispatch(Action.Loading(value))
    }

    private fun handleEmployeesSuccess(employeesList: Flow<PagingData<MovieModel>>) {
        viewModelScope.launch(Dispatchers.Default) {
            employeesList.cachedIn(viewModelScope).collect {
                // Update PagingData
                _pagingData.value = it

                // Update Screen State
                dispatch(Action.MoviesLoaded(it))
            }
        }
    }

    private fun handleEmployeesError(error: Error) {
        dispatch(Action.ErrorOccurred(error))
    }

    data class State(
        val isLoading: Boolean = true,
        val error: Error? = null,
        val selectedMovie: MovieModel? = null,
        val pagingData: PagingData<MovieModel> = PagingData.empty()
    ) : BaseState

    sealed class Action : BaseAction {
        data class MoviesLoaded(val pagingData: PagingData<MovieModel>) :
            Action()

        data class ErrorOccurred(val error: Error) : Action()
        data class Loading(val value: Boolean) : Action()
        data class SelectMovie(val model: MovieModel) : Action()
    }
}