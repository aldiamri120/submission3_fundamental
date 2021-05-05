package com.dicoding.appgithub_2.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.appgithub_2.R
import com.dicoding.appgithub_2.databinding.ActivityMainBinding
import com.dicoding.appgithub_2.model.UserModel
import com.dicoding.appgithub_2.view.adapter.UserListAdapter
import com.dicoding.appgithub_2.view.setting.SettingActivity
import com.dicoding.appgithub_2.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // memanggil adapter
        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.apply {

            // language setting
            val nameSearch = resources.getString(R.string.find_your_connection)
            val textHome = resources.getString(R.string.greet)
            binding.etSearchQuery.hint = nameSearch
            binding.textHome.text = textHome

            // membuat keyword dari setiap pencarian
            etSearchQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    userSearch()
                    true.showBuffer()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            // menampilan recyclerview
            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubUser.adapter = userListAdapter

            // membuat agar gambar search bisa diklik
            btnIcSearch.setOnClickListener {
                userSearch()
            }

            btnAlarm.setOnClickListener {
                Intent(this@MainActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        // membuat agar setiap item pada recycle bisa diklik dan menuju detail activity
        userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.apply {
                    putExtra(UserDetailActivity.USERNAME_PACKAGE, data.login)
                    putExtra(UserDetailActivity.ID_PACKAGE, data.id)
                    putExtra(UserDetailActivity.AVA_PACKAGE, data.avatar_url)
                }
                startActivity(intent)
            }
        })

        // memanggil view model, yang fungsinya ketika terjadi perubahan pada handphone(misalnya
        // rotasi), maka data tidak akan hilang
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)
        userViewModel.getSearchUser().observe(this, {
            if (it != null) {
                userListAdapter.setterList(it)
                false.showBuffer()
            }
        })
    }

    // membuat agar huruf setiap pencarian tampil
    private fun userSearch() {
        binding.apply {
            val query = etSearchQuery.text.toString()

            if (query.isEmpty())
                false.showBuffer()
            userViewModel.setUserSearch(query)
        }
    }

    // mengatur loading agar tampil dan hilang, serta segala macam hal yang dibutuhkan
    private fun Boolean.showBuffer() {
        if (this) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                imageHome.visibility = View.VISIBLE
                textHome.visibility = View.VISIBLE
                rvGithubUser.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                imageHome.visibility = View.INVISIBLE
                textHome.visibility = View.INVISIBLE
                rvGithubUser.visibility = View.VISIBLE
            }
        }
    }

    // menu bahasa & favorite
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // agar kedua menu tersebut bisa diklik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.switch_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.fav_list -> {
                val mFav = Intent(this, FavoriteActivity::class.java)
                startActivity(mFav)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}