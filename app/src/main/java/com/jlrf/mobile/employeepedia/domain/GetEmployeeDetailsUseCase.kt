package com.jlrf.mobile.employeepedia.domain

import arrow.core.Either
import com.jlrf.mobile.employeepedia.domain.base.UseCase
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.domain.repositories.EmployeeRepository
import javax.inject.Inject

class GetEmployeeDetailsUseCase @Inject constructor(
    private val repository: EmployeeRepository
) : UseCase<EmployeeModel, GetEmployeeDetailsUseCase.Params>() {

    override suspend fun run(params: Params): Either<Error, EmployeeModel> {
        val response = repository.getEmployeeDetails(params.id)
        return response?.let {
            Either.Right(it)
        } ?: run {
            Either.Left(EmployeeDetailsError.EmployeeNotFoundError)
        }
    }

    data class Params(val id: Long)

    sealed class EmployeeDetailsError : Error() {
        object EmployeeNotFoundError : GetEmployeesUseCase.EmployeeError()
    }
}