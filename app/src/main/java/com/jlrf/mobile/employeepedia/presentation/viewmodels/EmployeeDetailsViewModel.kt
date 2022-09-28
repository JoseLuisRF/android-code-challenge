package com.jlrf.mobile.employeepedia.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.jlrf.mobile.employeepedia.domain.GetEmployeeDetailsUseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.base.BaseAction
import com.jlrf.mobile.employeepedia.presentation.base.BaseState
import com.jlrf.mobile.employeepedia.presentation.base.BaseViewModel
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase
) : BaseViewModel<EmployeeDetailsViewModel.State, EmployeeDetailsViewModel.Action>(State(), dispatcher) {

    override fun reduce(oldState: State, action: Action): State {
        return when (action) {
            is Action.ErrorOccurred -> oldState.copy(
                isLoading = false,
                error = action.error
            )
            is Action.EmployeeDetailsLoaded -> oldState.copy(
                isLoading = false,
                employee = action.employeeDetails
            )
        }
    }

    fun getEmployeeDetails(employeeId: Long) {
        viewModelScope.launch(dispatcher.main()) {
            getEmployeeDetailsUseCase.run(
                GetEmployeeDetailsUseCase.Params(
                    id = employeeId
                )
            ).fold(
                { error -> handleEmployeesError(error) },
                ::handleEmployeeDetailsSuccess
            )
        }
    }

    private fun handleEmployeeDetailsSuccess(employeeDetails: EmployeeModel) {
        dispatch(Action.EmployeeDetailsLoaded(employeeDetails = employeeDetails))
    }

    private fun handleEmployeesError(error: Error) {
        dispatch(Action.ErrorOccurred(error))
    }

    data class State(
        val employee: EmployeeModel? = null,
        val isLoading: Boolean = true,
        val error: Error? = null
    ) : BaseState

    sealed class Action : BaseAction {
        data class EmployeeDetailsLoaded(val employeeDetails: EmployeeModel) : Action()
        data class ErrorOccurred(val error: Error) : Action()
    }
}