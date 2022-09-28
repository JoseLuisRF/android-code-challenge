package com.jlrf.mobile.employeepedia.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import arrow.core.None
import com.jlrf.mobile.employeepedia.domain.GetEmployeeDetailsUseCase
import com.jlrf.mobile.employeepedia.domain.GetEmployeesUseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.presentation.base.BaseAction
import com.jlrf.mobile.employeepedia.presentation.base.BaseState
import com.jlrf.mobile.employeepedia.presentation.base.BaseViewModel
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class EmployeesListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase
) : BaseViewModel<EmployeesListViewModel.State, EmployeesListViewModel.Action>(State(), dispatcher) {

    init {
        loadEmployees()
    }

    override fun reduce(oldState: State, action: Action): State {
        return when (action) {
            is Action.EmployeesLoaded -> oldState.copy(
                isLoading = false,
                employees = action.employees
            )
            is Action.ErrorOccurred -> oldState.copy(
                isLoading = false,
                error = action.error
            )
            is Action.EmployeeDetailsLoaded -> oldState.copy(
                isLoading = false,
                employees = emptyList()
            )
        }
    }

    private fun loadEmployees() {
        viewModelScope.launch(dispatcher.main()) {
            getEmployeesUseCase.run(None).fold(
                { error -> handleEmployeesError(error) },
                ::handleEmployeesSuccess
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
        val error: Error? = null
    ) : BaseState

    sealed class Action : BaseAction {
        data class EmployeesLoaded(val employees: List<EmployeeModel>) : Action()
        data class EmployeeDetailsLoaded(val employeeDetails: EmployeeModel) : Action()
        data class ErrorOccurred(val error: Error) : Action()
    }
}