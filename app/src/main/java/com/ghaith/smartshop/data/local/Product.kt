package com.ghaith.smartshop.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantity: Int,
    val price: Double,
    val lastUpdated: Long = System.currentTimeMillis()
)
