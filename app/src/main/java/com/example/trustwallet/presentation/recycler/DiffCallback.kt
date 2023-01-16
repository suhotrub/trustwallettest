package com.example.trustwallet.presentation.recycler

import androidx.recyclerview.widget.DiffUtil

/**
 * @author a.gorbachev
 */
class DiffCallback : DiffUtil.ItemCallback<CommandLineItem>() {

    override fun areItemsTheSame(oldItem: CommandLineItem, newItem: CommandLineItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommandLineItem, newItem: CommandLineItem): Boolean {
        return oldItem.text == newItem.text && oldItem.colorRes == newItem.colorRes
    }
}
