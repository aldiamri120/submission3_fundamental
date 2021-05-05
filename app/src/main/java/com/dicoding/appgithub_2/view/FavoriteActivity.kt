package com.dicoding.appgithub_2.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.appgithub_2.data.UserFav
import com.dicoding.appgithub_2.databinding.ActivityFavoriteBinding
import com.dicoding.appgithub_2.model.UserModel
import com.dicoding.appgithub_2.view.adapter.UserListAdapter
import com.dicoding.appgithub_2.viewModel.FavoriteViewModel
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favBinding: ActivityFavoriteBinding
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favBinding.root)

        favAdapter = UserListAdapter()
        favAdapter.notifyDataSetChanged()

        favAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.apply {
                    putExtra(UserDetailActivity.USERNAME_PACKAGE, data.login)
                    putExtra(UserDetailActivity.ID_PACKAGE, data.id)
                    putExtra(UserDetailActivity.AVA_PACKAGE, data.avatar_url)
                    startActivity(intent)
                }
            }
        })

        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favViewModel.getListFavUser()?.observe(this, {
            if (it != null) {
                val listMap = listMap(it)
                favAdapter.setterList(listMap)
            }
        })

        favBinding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = favAdapter
        }
    }

    private fun listMap(users: List<UserFav>): ArrayList<UserModel> {
        val userList = ArrayList<UserModel>()
        for (user in users) {
            val mapUser = UserModel(user.login, user.id, user.avatar_url)

            userList.add(mapUser)
        }
        return userList
    }
}