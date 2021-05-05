package com.dicoding.appgithub_2.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.appgithub_2.R
import com.dicoding.appgithub_2.databinding.FragmentFollowersBinding
import com.dicoding.appgithub_2.view.UserDetailActivity
import com.dicoding.appgithub_2.view.adapter.UserListAdapter
import com.dicoding.appgithub_2.viewModel.FollowersViewModel

@Suppress("DEPRECATION")
class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var username: String
    private lateinit var followersViewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(UserDetailActivity.USERNAME_PACKAGE).toString()

        _binding = FragmentFollowersBinding.bind(view)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()
        binding.rvGithubFollowers.setHasFixedSize(true)
        binding.rvGithubFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvGithubFollowers.adapter = userListAdapter

        showData(true)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)
        followersViewModel.setListFollowers(username)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it != null) {
                userListAdapter.setterList(it)
                showData(false)
            }
        })
    }

    private fun showData(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}