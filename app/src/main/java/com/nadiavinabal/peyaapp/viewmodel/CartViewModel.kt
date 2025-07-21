package com.nadiavinabal.peyaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadiavinabal.peyaapp.database.entities.CartItemEntity
import com.nadiavinabal.peyaapp.domain.repository.CartRepository
import com.nadiavinabal.peyaapp.domain.repository.ProductRepository
import com.nadiavinabal.peyaapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    val cartItems = cartRepository.getCartItems().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val totalItems = cartRepository.getTotalItems().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val totalPrice = cartRepository.getTotalPrice().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0.0
    )

    private val _isCheckoutDone = MutableStateFlow(false)
    val isCheckoutDone: StateFlow<Boolean> = _isCheckoutDone

    fun addToCart(productId: String) = viewModelScope.launch {
        cartRepository.addToCart(productId)
    }

    fun increaseQuantity(cartItem: CartItemEntity) = viewModelScope.launch {
        cartRepository.updateQuantity(cartItem, cartItem.quantity + 1)
    }

    fun decreaseQuantity(cartItem: CartItemEntity) = viewModelScope.launch {
        cartRepository.updateQuantity(cartItem, cartItem.quantity - 1)
    }

    fun removeItem(cartItem: CartItemEntity) = viewModelScope.launch {
        cartRepository.removeFromCart(cartItem)
    }

    fun clearCart() = viewModelScope.launch {
        cartRepository.clearCart()
    }

    fun isProductInCart(productId: String): Boolean {
        return cartItems.value.any { it.cartItemEntity.productId == productId }
    }

    fun getProductQuantity(productId: String): Int {
        return cartItems.value.firstOrNull { it.cartItemEntity.productId == productId }?.cartItemEntity?.quantity ?: 0
    }

    fun checkout() = viewModelScope.launch {
        cartRepository.checkout()
        _isCheckoutDone.value = true
    }


}