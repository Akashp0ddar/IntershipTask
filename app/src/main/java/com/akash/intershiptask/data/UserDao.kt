package com.akash.intershiptask.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akash.intershiptask.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(user: User)

    @Query("SELECT*FROM post_table")
     fun readAllData(): LiveData<List<User>>


}