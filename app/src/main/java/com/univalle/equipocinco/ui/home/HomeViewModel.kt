package com.univalle.equipocinco.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.equipocinco.data.local.database.InventoryDatabase
import com.univalle.equipocinco.data.local.entity.Product
import com.univalle.equipocinco.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository

    // Lista de productos (StateFlow para observación reactiva)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Estado de carga (para mostrar/ocultar el ProgressBar)
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        val productDao = InventoryDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)  // ✅ Constructor directo

        viewModelScope.launch {
            repository.getAllProducts().collect { productList ->
                _products.value = productList
                _isLoading.value = false
            }
        }
    }

}