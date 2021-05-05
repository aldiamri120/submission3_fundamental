package com.dicoding.appgithub_2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.appgithub_2.data.UserFav
import com.dicoding.appgithub_2.data.UserFavDao
import com.dicoding.appgithub_2.data.database.UserFavDatabase
import com.dicoding.appgithub_2.model.UserDetail
import com.dicoding.appgithub_2.model.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UserDetailViewModel(app: Application) : AndroidViewModel(app) {
    val userDetail = MutableLiveData<UserDetail>()

    private var userFavDao: UserFavDao?
    private var userFavDB: UserFavDatabase? = UserFavDatabase.getDB(app)

    init {
        userFavDao = userFavDB?.userFavDao()
    }

    fun setUserDetail(username: String) {
        ApiClient.apiReq
            .getUserDetail(username)
            .enqueue(object : retrofit2.Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, error: Throwable) {
                    Log.d("Failure", error.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail

    fun addToFav(username: String, id: Int, ava: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val gitUser = UserFav(username, id, ava)
            userFavDao?.addUserToFav(gitUser)
            Log.d("Failure", gitUser.toString())
        }
    }

    suspend fun checkUser(id: Int) = userFavDao?.checkUserOnFav(id)

    fun removeFromFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userFavDao?.removeUserFromFav(id)
            Log.d("Failure", id.toString())
        }
    }
}
