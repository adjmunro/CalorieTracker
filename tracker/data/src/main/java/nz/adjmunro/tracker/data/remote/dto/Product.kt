package nz.adjmunro.tracker.data.remote.dto

import com.squareup.moshi.Json

typealias ThumbnailUrl = String?
typealias ProductName = String?

data class Product(
    @field:Json(name = "image_front_thumb_url") val imageUrl: ThumbnailUrl,
    val nutriments: Nutriments,
    @field:Json(name = "product_name") val name: ProductName,
)
