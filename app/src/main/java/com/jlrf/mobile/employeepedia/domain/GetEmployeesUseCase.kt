package com.jlrf.mobile.employeepedia.domain

import arrow.core.Either
import arrow.core.None
import com.jlrf.mobile.employeepedia.domain.base.EmployeeFilterType
import com.jlrf.mobile.employeepedia.domain.base.UseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.domain.repositories.EmployeeRepository
import com.jlrf.mobile.employeepedia.util.toError
import javax.inject.Inject

class GetEmployeesUseCase @Inject constructor(
    private val repository: EmployeeRepository
) : UseCase<List<EmployeeModel>, GetEmployeesUseCase.Params>() {

    override suspend fun run(params: Params): Either<Error, List<EmployeeModel>> = try {
        val response = repository.getEmployees()
        response.takeIf { it.isNotEmpty() }?.let {
            when(params.filter) {
                EmployeeFilterType.None -> Either.Right(response)
                EmployeeFilterType.Salary -> Either.Right(response.filter { it.salary >= 700000 })
                EmployeeFilterType.Age -> Either.Right(response.filter { it.age >= 66 })
            }
        } ?: run {
            Either.Left(EmployeeError.NoEmployeesAvailable)
        }
    } catch (throwable: Throwable) {
        Either.Left(throwable.toError())
    }

    sealed class EmployeeError : Error() {
        object NoEmployeesAvailable : EmployeeError()
    }

    data class Params(val filter: EmployeeFilterType)
}