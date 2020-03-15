package com.brown.stackuser.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.brown.stackuser.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        val uri = Uri.parse(url).buildUpon().scheme("https").build()
        Glide.with(context)
            .load(uri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            ).into(this)
    }
}


@BindingAdapter("date")
fun TextView.setDate(dateLong: Long?) {
    dateLong?.let {
        val date = Date(it * 1000L)
        val dateString = SimpleDateFormat.getDateInstance().format(date)
        text = dateString
    }
}


@BindingAdapter("reputation")
fun TextView.setReputation(reputation: Int?) {
    reputation?.let {
        text = String.format("%,d", it)
    }
}


@BindingAdapter("reputation_change")
fun TextView.setReputationChange(change: Int?) {
    change?.let {
        text = change.toString()
        setTextColor(
            ContextCompat.getColor(
                context,
                when {
                    change < 0 -> R.color.reputationDown
                    change > 0 -> R.color.reputationUp
                    else -> R.color.reputationUnChange
                }
            )
        )
    }
}


@BindingAdapter("is_visible")
fun View.setVisibility(show: Boolean?) {
    show?.let {
        visibility = if (it) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
