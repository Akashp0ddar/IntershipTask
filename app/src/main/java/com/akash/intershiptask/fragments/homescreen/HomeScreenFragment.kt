package com.akash.intershiptask.fragments.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akash.intershiptask.R
import com.akash.intershiptask.databinding.FragmentHomeScreenBinding
import com.akash.intershiptask.model.User
import com.akash.intershiptask.viewmodel.UserViewModel

class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var mUserViewModel: UserViewModel
    private val adapter = MyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHomeScreen.adapter = adapter

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user as ArrayList<User>)
        })
        onClick()
    }


    private fun onClick() {
        binding.ivPlus.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_addPostFragment)
        }
    }

}