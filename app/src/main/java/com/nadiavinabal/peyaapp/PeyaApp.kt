package com.nadiavinabal.peyaapp

import android.app.Application
import com.nadiavinabal.peyaapp.domain.repository.ProductRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PeyaApp: Application(){
    @Inject
    lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.insertInitialProducts()
        }
    }
}