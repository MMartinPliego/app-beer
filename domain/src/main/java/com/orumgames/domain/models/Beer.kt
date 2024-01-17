package com.orumgames.domain.models

data class Beer(
    val id: Int,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val abv: Double?
)
