package com.mazaady.domain.entity.cat

data class Data(
    val description: String,
    val id: Int,
    val list: Boolean,
    val name: String,
    val options: List<Option>,
    val other_value: Any,
    val parent: Any,
    val slug: String,
    val type: String,
    val value: String
)