package com.dicoding.consumerapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.consumerapp.database.DBContract
import com.dicoding.consumerapp.database.MappingHelper
import com.dicoding.consumerapp.model.UserModel

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private var favList = MutableLiveData<ArrayList<UserModel>>()

    fun setListFavUser(context: Context) {
        val dataCursor = context.contentResolver.query(
                DBContract.Column.CONTENT_URI,
                null,
                null,
                null,
                null
        )
        val cursorToArrayList = MappingHelper.mapCursorToArrayList(dataCursor)
        favList.postValue(cursorToArrayList)
    }

    fun getListFavUser(): LiveData<ArrayList<UserModel>> = favList
}