package com.nadiavinabal.peyaapp.di

import com.nadiavinabal.peyaapp.domain.repository.ProductRepository
import com.nadiavinabal.peyaapp.domain.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}