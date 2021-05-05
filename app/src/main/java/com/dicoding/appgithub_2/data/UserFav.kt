package com.dicoding.appgithub_2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class UserFav(
        val login: String,

        @PrimaryKey
        val id: Int,
        val avatar_url: String
): Serializable