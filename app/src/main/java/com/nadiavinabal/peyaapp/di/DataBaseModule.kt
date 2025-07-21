package com.nadiavinabal.peyaapp.di

import android.content.Context
import androidx.room.Room
import com.nadiavinabal.peyaapp.database.AppDatabase
import com.nadiavinabal.peyaapp.database.dao.CartDao
import com.nadiavinabal.peyaapp.database.dao.OrderHistoryDao
import com.nadiavinabal.peyaapp.database.dao.OrderItemDao
import com.nadiavinabal.peyaapp.database.dao.ProductDao
import com.nadiavinabal.peyaapp.domain.repository.CartRepository
import com.nadiavinabal.peyaapp.domain.repository.CartRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase:: class.java, "peya-database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()

    @Provides
    fun provideOrderHistoryDao(database: AppDatabase): OrderHistoryDao = database.orderHistoryDao()

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao,
                              orderDao: OrderHistoryDao,
                              orderItemDao: OrderItemDao): CartRepository = CartRepositoryImpl(cartDao, orderDao, orderItemDao)

    @Provides
    fun provideOrderItemDao(database: AppDatabase): OrderItemDao {
        return database.orderItemDao()
    }


}