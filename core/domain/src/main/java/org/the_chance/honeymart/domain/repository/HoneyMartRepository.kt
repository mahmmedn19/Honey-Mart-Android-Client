package org.the_chance.honeymart.domain.repository

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.WishListEntity


interface HoneyMartRepository {

    suspend fun getAllMarkets(): List<MarketEntity>?
    suspend fun getCategoriesInMarket(marketId: Long): List<CategoryEntity>?
    suspend fun getAllProductsByCategory(categoryId: Long): List<ProductEntity>?
    suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryEntity>?
    suspend fun addToWishList(productId: Long): String
    suspend fun deleteFromWishList(productId: Long): String
    suspend fun getWishList(): List<WishListEntity>
    suspend fun getOrderDetails(orderId: Long): OrderDetailsEntity
    suspend fun getAllCartProducts(): List<CartEntity>
    suspend fun addProductToCart(productId: Long, count: Long): String
    suspend fun getProductDetails(productId: Long): ProductEntity
}