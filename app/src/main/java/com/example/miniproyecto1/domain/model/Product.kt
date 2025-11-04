package com.example.miniproyecto1.domain.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
) {
    fun getTotal(): Double = price * quantity

    fun getFormattedPrice(): String {
        return "$ ${formatNumber(price)}"
    }

    fun getFormattedTotal(): String {
        return "$ ${formatNumber(getTotal())}"
    }

    private fun formatNumber(number: Double): String {
        return String.format("%,.2f", number)
    }
}