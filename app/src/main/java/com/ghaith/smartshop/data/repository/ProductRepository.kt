package com.ghaith.smartshop.data.repository

import com.ghaith.smartshop.data.local.Product
import com.ghaith.smartshop.data.local.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val dao: ProductDao
) {

    fun getAll(): Flow<List<Product>> = dao.getAll()

    suspend fun insert(product: Product) = dao.insert(product)

    suspend fun update(product: Product) = dao.update(product)

    suspend fun delete(product: Product) = dao.delete(product)

    suspend fun count() = dao.count()

    suspend fun totalValue() = dao.totalValue() ?: 0.0
}
