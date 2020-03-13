package com.brown.stackuser.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.brown.stackuser.Injection
import com.brown.stackuser.R
import com.brown.stackuser.adapter.UserAdapter
import com.brown.stackuser.adapter.UserViewHolder
import com.brown.stackuser.databinding.UserListFragmentBinding
import com.brown.stackuser.model.UserFilter

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

        setUpEventObserver()

        setUpRecyclerView()

        setHasOptionsMenu(true)

        return binding.root
    }


    private fun setUpRecyclerView() {
        val adapter = UserAdapter(
            UserViewHolder.OnClickListener { user ->
                viewModel.navigationToDetail(user)
            },
            UserViewHolder.CheckBoxListener { user ->
                viewModel.updateFavoriteUser(user)
            }
        )

        viewModel.users.observe(viewLifecycleOwner, Observer { users ->
            adapter.submitList(users)
        })

        binding.userList.adapter = adapter
    }


    private fun setUpEventObserver() {
        viewModel.navigationToDetailEvent.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                val action =
                    UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(it)
                findNavController().navigate(action)
                viewModel.navigationToDetailDone()
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.all_filter -> {
                viewModel.updateFilter(UserFilter.ALL)
                true
            }
            R.id.favorite_filter -> {
                viewModel.updateFilter(UserFilter.FAVORITE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
