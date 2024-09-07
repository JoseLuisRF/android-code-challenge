package com.jlrf.mobile.employeepedia.di

import com.jlrf.mobile.employeepedia.data.repositories.MoviesRepositoryImpl
import com.jlrf.mobile.employeepedia.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(
    includes = [
        NetworkModule::class,
        ServiceModule::class
    ]
)
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesEmployeeRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository
}