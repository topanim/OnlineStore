package com.example.data.api

import com.example.domain.models.Category
import com.example.domain.models.CategoryRequest
import com.example.domain.models.Product
import com.example.domain.models.ProductRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: HttpClient
) {
    companion object Endpoints {
        private const val API = "/api/v1"
        const val _PRODUCTS = "/products"
        const val PRODUCTS = "$API/products"
        const val _CATEGORIES = "/categories"
        const val CATEGORIES = "$API/categories"
    }

    suspend fun fetchProducts(
        limit: Int,
        offset: Int,
        title: String? = null,
        categoryId: Int? = null,
        priceMin: Int? = null,
        priceMax: Int? = null,
    ) = client.get(PRODUCTS) {
        parameter("limit", limit)
        parameter("offset", offset)
        title?.let { parameter("title", it) }
        categoryId?.let { parameter("categoryId", categoryId) }
        priceMin?.let { parameter("priceMin", it) }
        priceMax?.let { parameter("priceMax", it) }
    }

    suspend fun fetchProduct(
        id: Int
    ) = client.get(PRODUCTS) {
        url { appendPathSegments(id.toString()) }
    }.body<Product>()

    suspend fun postProduct(
        request: ProductRequest
    ) = client.post(PRODUCTS) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    suspend fun updateProduct(
        id: Int,
        request: ProductRequest
    ) = client.put("$PRODUCTS/$id") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    suspend fun deleteProduct(
        id: Int
    ) = client.delete("$PRODUCTS/$id")

    suspend fun fetchCategoryProducts(
        categoryId: Int,
        limit: Int,
        offset: Int
    ) = client.get(CATEGORIES) {
        url { appendPathSegments(categoryId.toString(), _PRODUCTS) }
        parameter("limit", limit)
        parameter("offset", offset)
    }.body<List<Product>>()

    suspend fun fetchCategories(
        limit: Int = 30
    ) = client.get(CATEGORIES) {
        parameter("limit", limit)
    }

    suspend fun fetchCategory(
        id: Int
    ) = client.get(CATEGORIES) {
        url { appendPathSegments(id.toString()) }
    }.body<Category>()

    suspend fun postCategory(
        request: CategoryRequest
    ) = client.post(CATEGORIES) {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body<Category>()

    suspend fun updateCategory(
        id: Int,
        request: CategoryRequest
    ) = client.put("$CATEGORIES/$id") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }

    suspend fun deleteCategory(
        id: Int
    ) = client.delete("$CATEGORIES/$id")
}