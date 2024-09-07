package com.jlrf.mobile.employeepedia.di

import com.jlrf.mobile.employeepedia.data.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class ServiceModule {

    @Provides
    fun providesEmployeeService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}