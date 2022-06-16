package com.elnaz.theguardiannewsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elnaz.theguardiannewsfeed.databinding.ItemLoadingBinding

class LoaderStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    inner class LoaderViewHolder(
        private val binding: ItemLoadingBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState){

            binding.errorMsg.isVisible = loadState is LoadState.Error
            binding.retryBtn.isVisible = loadState is LoadState.Error
            binding.progressBar.isVisible = loadState is LoadState.Loading

            binding.retryBtn.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemLoadingBinding.inflate(inflater,parent, false)
        return LoaderViewHolder(view)
    }
}