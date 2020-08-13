package com.example.axxesschallenge.database

import androidx.room.*

@Dao
interface ImageDao {

    @Query("SELECT comment FROM image_comments WHERE account_id = :accountId")
    fun retrieveImageComment(accountId: Int): String

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageComment(imageComment: ImageDBEntity): Long

}