package com.brown.stackuser.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brown.stackuser.model.Reputation

/**
 * Adapter for the list of user's reputation.
 */
class ReputationAdapter : PagedListAdapter<Reputation, ReputationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReputationViewHolder {
        return ReputationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReputationViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Reputation>() {
            override fun areItemsTheSame(oldItem: Reputation, newItem: Reputation): Boolean {
                return oldItem.userId == newItem.userId && oldItem.postId == newItem.postId
            }

            override fun areContentsTheSame(oldItem: Reputation, newItem: Reputation): Boolean {
                return oldItem == newItem
            }
        }
    }

}
