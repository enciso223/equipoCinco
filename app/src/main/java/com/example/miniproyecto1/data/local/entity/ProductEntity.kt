package com.example.miniproyecto1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
) {
    fun getTotal(): Double = price * quantity
}