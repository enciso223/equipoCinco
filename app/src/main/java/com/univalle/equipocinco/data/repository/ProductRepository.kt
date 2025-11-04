package com.univalle.equipocinco.data.repository

import com.univalle.equipocinco.data.local.dao.ProductDao
import com.univalle.equipocinco.data.local.entity.Product
import com.univalle.equipocinco.data.remote.api.InventoryApiService
import com.univalle.equipocinco.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
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
    private fun Product.toProduct(): Product {
        return com.univalle.equipocinco.domain.model.Product(
            id = this.id,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )
    }

    private fun Product.toEntity(): Product {
        return Product(
            id = this.id,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )
    }
}