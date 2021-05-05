package com.dicoding.appgithub_2.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.appgithub_2.data.UserFav
import com.dicoding.appgithub_2.data.UserFavDao
import com.dicoding.appgithub_2.data.database.UserFavDatabase

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private var userFavDao: UserFavDao?
    private var userFavDB: UserFavDatabase? = UserFavDatabase.getDB(app)

    init {
        userFavDao = userFavDB?.userFavDao()
    }

    fun getListFavUser(): LiveData<List<UserFav>>? {
        return userFavDao?.getUserOnFav()
    }
}