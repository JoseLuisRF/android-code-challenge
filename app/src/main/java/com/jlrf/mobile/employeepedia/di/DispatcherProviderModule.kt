package com.jlrf.mobile.employeepedia.di

import com.jlrf.mobile.employeepedia.util.DefaultDispatcherProvider
import com.jlrf.mobile.employeepedia.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DispatcherProviderModule {

    @Binds
    abstract fun provideDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider): DispatcherProvider
}