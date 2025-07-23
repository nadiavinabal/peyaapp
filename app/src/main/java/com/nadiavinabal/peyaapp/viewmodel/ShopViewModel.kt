package com.nadiavinabal.peyaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadiavinabal.peyaapp.domain.repository.ProductRepository
import com.nadiavinabal.peyaapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

   /* private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
   */
    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                productRepository.getAllProducts()
                    .collect { products ->
                        _products.value = products

                    }
            } catch (e: Exception) {
                //_error.value = "Error loading products: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

   /* fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterProducts(query)
    } */

    /*private fun filterProducts(query: String) {
        if (query.isBlank()) {
            _filteredProducts.value = _products.value
        } else {
            _filteredProducts.value = _products.value.filter { product ->
                product.name.contains(query, ignoreCase = true)
            }
        }
    }*/
}