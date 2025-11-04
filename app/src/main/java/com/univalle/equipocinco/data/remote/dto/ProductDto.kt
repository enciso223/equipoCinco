package com.univalle.equipocinco.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quantity")
    val quantity: Int
)