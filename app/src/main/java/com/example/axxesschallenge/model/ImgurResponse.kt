package com.example.axxesschallenge.model

data class ImgurResponse(
    val data: List<ImageResponse>,
    val status: Int,
    val success: Boolean
)