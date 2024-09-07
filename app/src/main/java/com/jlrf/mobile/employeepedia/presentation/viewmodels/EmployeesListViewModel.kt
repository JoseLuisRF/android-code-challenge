package com.jlrf.mobile.employeepedia.presentation.viewmodels

import arrow.core.None
import com.jlrf.mobile.employeepedia.domain.GetEmployeesUseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.base.BaseAction
import com.jlrf.mobile.employeepedia.presentation.base.BaseState
import com.jlrf.mobile.employeepedia.presentation.base.BaseViewModel
import com.jlrf.mobile.employeepedia.presentation.viewmodels.EmployeeDetailsViewModel.Action
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeesListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getEmployeesUseCase: GetEmployeesUseCase,
) : BaseViewModel<EmployeesListViewModel.State, EmployeesListViewModel.Action>(
    State(),
    dispatcher
) {

    override fun reduce(oldState: State, action: Action): State {
        return when (action) {
            is Action.EmployeesLoaded -> oldState.copy(
                isLoading = false,
                employees = action.employees,
                selectedEmployee = null
            )

            is Action.ErrorOccurred -> oldState.copy(
                isLoading = false,
                error = action.error
            )

            is Action.Loading -> oldState.copy(
                isLoading = action.value
            )

            is Action.SelectEmployee -> oldState.copy(
                selectedEmployee = action.employee
            )
        }
    }

    public fun selectEmployee(employeeId: Long) {
        currentState.employees.firstOrNull { it.id == employeeId }?.let {
            dispatch(Action.SelectEmployee(it))
        }
    }

    public suspend fun loadEmployees() {
        isLoading(true)
        getEmployeesUseCase.run(None).fold(
            { error -> handleEmployeesError(error) },
            ::handleEmployeesSuccess
        )
    }

    override fun isLoading(value: Boolean) {
        dispatch(Action.Loading(value))
    }

    private fun handleEmployeesSuccess(employeesList: List<EmployeeModel>) {
        dispatch(Action.EmployeesLoaded(employees = employeesList))
    }

    private fun handleEmployeesError(error: Error) {
        dispatch(Action.ErrorOccurred(error))
    }

    data class State(
        val employees: List<EmployeeModel> = emptyList(),
        val isLoading: Boolean = true,
        val error: Error? = null,
        val selectedEmployee: EmployeeModel? = null,
    ) : BaseState

    sealed class Action : BaseAction {
        data class EmployeesLoaded(val employees: List<EmployeeModel>) : Action()
        data class ErrorOccurred(val error: Error) : Action()
        data class Loading(val value: Boolean) : Action()
        data class SelectEmployee(val employee: EmployeeModel) : Action()
    }
}