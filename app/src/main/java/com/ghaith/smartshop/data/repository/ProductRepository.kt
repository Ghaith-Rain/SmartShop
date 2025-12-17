package com.ghaith.smartshop.data.repository

import com.ghaith.smartshop.data.local.Product
import com.ghaith.smartshop.data.local.ProductDao
import com.ghaith.smartshop.data.remote.FirestoreSync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductRepository(
    private val dao: ProductDao
) {
    private val firestore = FirestoreSync()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getAll(): Flow<List<Product>> = dao.getAll()

    /* ADD */
    suspend fun insert(product: Product) {
        dao.insert(product)
        firestore.addProduct(product)
    }

    /* UPDATE */
    suspend fun update(product: Product) {
        dao.update(product)
        firestore.updateProduct(product)
    }

    /* DELETE */
    suspend fun delete(product: Product) {
        firestore.deleteProduct(product)
        dao.delete(product)
    }

    /* FIRESTORE → ROOM */
    fun startListening() {
        firestore.listen { remoteProduct, changeType ->
            scope.launch {
                when (changeType) {
                    FirestoreSync.ChangeType.ADDED, 
                    FirestoreSync.ChangeType.MODIFIED -> {
                        dao.insert(remoteProduct) // REPLACE strategy handles both
                    }
                    FirestoreSync.ChangeType.REMOVED -> {
                        dao.delete(remoteProduct)
                    }
                }
            }
        }
    }

    suspend fun count() = dao.count()

    suspend fun totalValue() = dao.totalValue() ?: 0.0
}
