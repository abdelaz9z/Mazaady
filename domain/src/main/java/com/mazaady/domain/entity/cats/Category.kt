package com.mazaady.domain.entity.cats

data class Category(
    val children: List<Children>,
    val circle_icon: String,
    val description: Any,
    val disable_shipping: Int,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
)