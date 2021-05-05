package com.dicoding.appgithub_2.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavDao {
    // menginput data
    @Insert
    suspend fun addUserToFav(userFav: UserFav)

    // mengecek data
    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    suspend fun checkUserOnFav(id: Int) : Int

    // menghapus data
    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun removeUserFromFav(id: Int) : Int

    // menampilkan favorite
    @Query("SELECT * FROM favorite")
    fun getUserOnFav(): LiveData<List<UserFav>>

    // consumerapp
    @Query("SELECT * FROM favorite")
    fun findFavoriteList(): Cursor
}