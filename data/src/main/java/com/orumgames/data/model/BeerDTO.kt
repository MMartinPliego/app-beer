package com.orumgames.data.model

import com.squareup.moshi.Json

data class BeerDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "abv") val abv: Double
)
