package com.jlrf.mobile.employeepedia.data.repositories

import com.jlrf.mobile.employeepedia.data.mappers.EmployeeMapper
import com.jlrf.mobile.employeepedia.data.remote.EmployeeApi
import com.jlrf.mobile.employeepedia.domain.models.EmployeeModel
import com.jlrf.mobile.employeepedia.domain.repositories.EmployeeRepository
import com.jlrf.tec.TecEmployeeRepositoryImpl
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val api: EmployeeApi,
    private val mapper: EmployeeMapper
) : EmployeeRepository {

    override suspend fun getEmployees(): List<EmployeeModel> {
        val localRepo = TecEmployeeRepositoryImpl()
        return localRepo.getEmployeesReadOnly().map {
            EmployeeModel(
                id = it.id,
                name = it.name,
                salary = it.salary,
                age = it.age,
                profileImage = it.profileImage
            )
        }
    }

    override suspend fun getEmployeeDetails(id: Long): EmployeeModel? {
        return api.getEmployeeDetails(id)?.let { mapper.mapTo(it) }
    }
}