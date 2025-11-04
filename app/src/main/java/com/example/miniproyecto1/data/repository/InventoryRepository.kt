package com.example.miniproyecto1.data.repository

import com.example.miniproyecto1.data.local.dao.ProductDao
import com.example.miniproyecto1.data.local.entity.ProductEntity
import com.example.miniproyecto1.data.remote.api.InventoryApiService
import com.example.miniproyecto1.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRepository(
    private val productDao: ProductDao,
    private val apiService: InventoryApiService? = null // Optional for now
) {

    // Local operations
    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { entities ->
            entities.map { it.toProduct() }
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)?.toProduct()
    }

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product.toEntity())
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product.toEntity())
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product.toEntity())
    }

    suspend fun getTotalInventoryValue(): Double {
        return productDao.getTotalInventoryValue() ?: 0.0
    }

    // Mapping functions
    private fun ProductEntity.toProduct(): Product {
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )
    }

    private fun Product.toEntity(): ProductEntity {
        return ProductEntity(
            id = this.id,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )
    }
}