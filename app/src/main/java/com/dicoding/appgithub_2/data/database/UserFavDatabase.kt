package com.dicoding.appgithub_2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.appgithub_2.data.UserFav
import com.dicoding.appgithub_2.data.UserFavDao

@Database(
    entities = [UserFav::class],
    version = 1
)

abstract class UserFavDatabase : RoomDatabase() {
    abstract fun userFavDao() : UserFavDao

    companion object {
        @Volatile
        private var INSTANCE : UserFavDatabase? = null

        fun getDB(mContext: Context) : UserFavDatabase? {

            if (INSTANCE == null) {
                synchronized(UserFavDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(mContext.applicationContext,
                            UserFavDatabase::class.java, "database_user").build()
                    }
                }
            }
            return INSTANCE
        }
    }

}