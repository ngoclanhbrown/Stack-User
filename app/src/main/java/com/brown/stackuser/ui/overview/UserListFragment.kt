package com.brown.stackuser.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brown.stackuser.Injection
import com.brown.stackuser.adapter.UserAdapter
import com.brown.stackuser.adapter.UserViewHolder
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

        val application = requireNotNull(activity).application

        viewModel = ViewModelProvider(
            this, Injection.provideUserListViewModelFactory(application)
        ).get(UserListViewModel::class.java)
        binding.viewModel = viewModel

        setUpRecyclerView()

        return binding.root

    }


    private fun setUpRecyclerView() {
        val adapter = UserAdapter(
            UserViewHolder.OnClickListener { user ->
                Toast.makeText(context, "You clicked user ${user.id}", Toast.LENGTH_SHORT).show()
            },
            UserViewHolder.CheckBoxListener { user ->
                //  TODO: Handle favorite event
            }
        )

        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            adapter.submitList(users)
        })

        binding.userList.itemAnimator = null
        binding.userList.adapter = adapter
    }

}
