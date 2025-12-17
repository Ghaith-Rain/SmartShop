package com.ghaith.smartshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghaith.smartshop.data.local.Product
import com.ghaith.smartshop.data.repository.ProductRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repo: ProductRepository
) : ViewModel() {

    init {
        repo.startListening()
    }

    val products: StateFlow<List<Product>> =
        repo.getAll().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage = _uiMessage.asStateFlow()

    fun addProduct(name: String, quantity: Int, price: Double) {
        if (name.isBlank() || quantity < 0 || price <= 0.0) {
            _uiMessage.value = "Invalid product data"
            return
        }

        viewModelScope.launch {
            repo.insert(Product(name = name, quantity = quantity, price = price))
        }
    }

    fun update(product: Product) = viewModelScope.launch {
        repo.update(product)
    }

    fun delete(product: Product) = viewModelScope.launch {
        repo.delete(product)
    }

    suspend fun stats(): Pair<Int, Double> {
        return repo.count() to repo.totalValue()
    }
}
