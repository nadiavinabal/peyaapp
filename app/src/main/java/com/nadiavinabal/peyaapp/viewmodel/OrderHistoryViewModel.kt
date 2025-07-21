package com.nadiavinabal.peyaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadiavinabal.peyaapp.database.dao.OrderHistoryDao
import com.nadiavinabal.peyaapp.database.entities.OrderEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderHistoryDao: OrderHistoryDao
) : ViewModel() {
    val orders: StateFlow<List<OrderEntity>> = orderHistoryDao.getAllOrder()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}