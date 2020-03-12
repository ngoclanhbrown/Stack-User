package com.brown.stackuser.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brown.stackuser.databinding.UserListFragmentBinding

class UserListFragment : Fragment() {

    private lateinit var binding: UserListFragmentBinding
    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UserListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root

    }

}
