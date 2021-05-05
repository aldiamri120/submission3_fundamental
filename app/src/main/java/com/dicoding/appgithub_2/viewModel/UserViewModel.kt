package com.dicoding.appgithub_2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.appgithub_2.model.UserArray
import com.dicoding.appgithub_2.model.UserModel
import com.dicoding.appgithub_2.model.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserModel>>()

    fun setUserSearch(query: String) {
        ApiClient.apiReq.getUserSearch(query).enqueue(object : Callback<UserArray> {
            override fun onResponse(call: Call<UserArray>, response: Response<UserArray>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserArray>, error: Throwable) {
                Log.d("Failure", error.message.toString())
            }

        })
    }

    fun getSearchUser(): LiveData<ArrayList<UserModel>> = listUser
}