package com.mazaady.domain.entity.cats

data class Data(
    val ads_banners: List<AdsBanner>,
    val categories: List<Category>,
    val google_version: String,
    val huawei_version: String,
    val ios_latest_version: String,
    val ios_version: String,
    val statistics: Statistics
)