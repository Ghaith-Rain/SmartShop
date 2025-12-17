package com.ghaith.smartshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Int,
    val price: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)
