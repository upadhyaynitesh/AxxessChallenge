package com.example.axxesschallenge.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "image_comments")
data class ImageDBEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "comment") val comment: String
)