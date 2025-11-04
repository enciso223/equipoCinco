package com.example.miniproyecto1.data.remote.api

import com.example.miniproyecto1.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.*

interface InventoryApiService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductDto>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDto>

    @POST("products")
    suspend fun createProduct(@Body product: ProductDto): Response<ProductDto>

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: ProductDto
    ): Response<ProductDto>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Unit>
}