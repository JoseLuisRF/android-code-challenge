package com.jlrf.mobile.employeepedia.data.mappers

import com.jlrf.mobile.employeepedia.data.remote.model.EmployeeDto
import com.jlrf.mobile.employeepedia.data.remote.model.GetEmployeesResponse
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import javax.inject.Inject

class EmployeeMapper @Inject constructor() {

    fun mapTo(dto: EmployeeDto): EmployeeModel? {
        return dto.takeIf { it.id != null && it.employeeName.isNullOrBlank().not() }
            ?.let {
                EmployeeModel(
                    id = it.id ?: -1,
                    name = it.employeeName.orEmpty(),
                    salary = it.employeeSalary ?: -1.0,
                    age = it.employeeAge ?: -1,
                    profileImage = it.profileImage.orEmpty()
                )
            }
    }

    fun mapTo(response: GetEmployeesResponse): List<EmployeeModel> {
        return response.data?.mapNotNull { mapTo(it) } ?: run { emptyList() }
    }
}