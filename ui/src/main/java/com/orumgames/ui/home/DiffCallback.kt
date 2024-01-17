package com.orumgames.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.orumgames.domain.models.Beer

class DiffCallback : DiffUtil.ItemCallback<Beer>() {

    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean =
        oldItem == newItem
}