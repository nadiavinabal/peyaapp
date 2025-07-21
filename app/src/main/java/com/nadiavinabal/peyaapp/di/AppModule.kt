package com.nadiavinabal.peyaapp.di

import com.nadiavinabal.peyaapp.data.ProfileDataSource
import com.nadiavinabal.peyaapp.data.ProfileDataSourceImpl
import com.nadiavinabal.peyaapp.data.api.AuthApiService
import com.nadiavinabal.peyaapp.data.api.AuthRepositoryImpl
import com.nadiavinabal.peyaapp.data.api.FoodApiService

import com.nadiavinabal.peyaapp.data.datasource.ProductDataSource
import com.nadiavinabal.peyaapp.data.datasource.ProductDataSourceImpl
import com.nadiavinabal.peyaapp.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideProfileDataSource(): ProfileDataSource {
        return ProfileDataSourceImpl()
    }
    @Provides
    @Singleton
    fun provideProductDataSource(apiService: FoodApiService): ProductDataSource {
        return ProductDataSourceImpl(apiService)
    }

}