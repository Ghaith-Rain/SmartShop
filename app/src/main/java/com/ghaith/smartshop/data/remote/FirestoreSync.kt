package com.ghaith.smartshop.data.remote

import com.ghaith.smartshop.data.local.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class FirestoreSync {

    private val firestore = FirebaseFirestore.getInstance()

    /* ADD */
    fun addProduct(product: Product) {
        firestore.collection("products")
            .document(product.id)
            .set(
                mapOf(
                    "name" to product.name,
                    "quantity" to product.quantity,
                    "price" to product.price,
                    "lastUpdated" to product.lastUpdated
                )
            )
    }

    /* UPDATE */
    fun updateProduct(product: Product) {
        firestore.collection("products")
            .document(product.id)
            .set(
                mapOf(
                    "name" to product.name,
                    "quantity" to product.quantity,
                    "price" to product.price,
                    "lastUpdated" to product.lastUpdated
                )
            )
    }

    /* DELETE */
    fun deleteProduct(product: Product) {
        firestore.collection("products")
            .document(product.id)
            .delete()
    }

    /* LISTEN */
    fun listen(
        onProductChanged: (Product, ChangeType) -> Unit
    ): ListenerRegistration {
        return firestore.collection("products")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.documentChanges?.forEach { change ->
                    val doc = change.document
                    val product = Product(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        quantity = doc.getLong("quantity")?.toInt() ?: 0,
                        price = doc.getDouble("price") ?: 0.0,
                        lastUpdated = doc.getLong("lastUpdated") ?: System.currentTimeMillis()
                    )
                    
                    val changeType = when (change.type) {
                        com.google.firebase.firestore.DocumentChange.Type.ADDED -> ChangeType.ADDED
                        com.google.firebase.firestore.DocumentChange.Type.MODIFIED -> ChangeType.MODIFIED
                        com.google.firebase.firestore.DocumentChange.Type.REMOVED -> ChangeType.REMOVED
                    }
                    
                    onProductChanged(product, changeType)
                }
            }
    }
    
    enum class ChangeType {
        ADDED, MODIFIED, REMOVED
    }
}

