package com.example.axxesschallenge.model

import java.io.Serializable

data class ImgurResponse(
    val data: List<ImageResponse>,
    val status: Int,
    val success: Boolean
) : Serializable