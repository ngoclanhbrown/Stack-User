package com.brown.stackuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brown.stackuser.databinding.ReputationListItemBinding
import com.brown.stackuser.model.Reputation

/**
 * View Holder for a [Reputation] RecyclerView list item.
 */
class ReputationViewHolder private constructor(
    private val binding: ReputationListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ReputationViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ReputationListItemBinding.inflate(inflater, parent, false)
            return ReputationViewHolder(binding)
        }
    }

    fun bind(reputation: Reputation) {
        binding.reputation = reputation
        binding.executePendingBindings()
    }

}
