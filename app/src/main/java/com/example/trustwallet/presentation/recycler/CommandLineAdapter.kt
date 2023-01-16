package com.example.trustwallet.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trustwallet.databinding.ItemCommandLineBinding

/**
 * @author a.gorbachev
 */
class CommandLineAdapter : ListAdapter<CommandLineItem, CommandLineAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCommandLineBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.viewBinding) {
            lineText.text = getItem(position).text
            lineText.setTextColor(root.context.getColor(getItem(position).colorRes))
        }
    }

    class ViewHolder(
        val viewBinding: ItemCommandLineBinding
    ) : RecyclerView.ViewHolder(viewBinding.root)
}
