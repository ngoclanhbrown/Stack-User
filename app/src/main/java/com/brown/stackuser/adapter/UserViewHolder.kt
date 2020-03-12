package com.brown.stackuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brown.stackuser.databinding.UserListItemBinding
import com.brown.stackuser.model.User

/**
 * View Holder for a [User] RecyclerView list item.
 */
class UserViewHolder private constructor(
    private val binding: UserListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): UserViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = UserListItemBinding.inflate(inflater, parent, false)
            return UserViewHolder(binding)
        }
    }

    fun bind(user: User, onClickListener: OnClickListener, checkBoxListener: CheckBoxListener) {
        binding.user = user
        binding.onClickListener = onClickListener
        binding.checkBoxListener = checkBoxListener
        binding.executePendingBindings()
    }

    class OnClickListener(
        private val onClickListener: (user: User) -> Unit
    ) {
        fun onClick(user: User) = onClickListener(user)
    }

    class CheckBoxListener(
        private val onClickListener: (user: User) -> Unit
    ) {
        fun onClick(user: User) = onClickListener(user)
    }

}
