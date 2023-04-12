package com.tiagomissiato.challenge.repository.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tiagomissiato.challenge.core.extensions.gone
import com.tiagomissiato.challenge.core.extensions.show
import com.tiagomissiato.challenge.core.extensions.simplify
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.databinding.RowRepositoryItemBinding
import kotlin.math.abs

class RepositoryListAdapter(private val dataSet: MutableList<GitRepo>) :
    RecyclerView.Adapter<RepositoryListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: RowRepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GitRepo) {
            binding.repoListRepoName.text = item.fullName
            binding.repoListOwnerName.text = item.owner.name
            binding.repoListRepoDescription.text = item.description
            binding.customInfoStar.text = item.starCount.simplify()
            binding.customInfoFork.text = item.forkCount.simplify()
            item.license?.let {
                binding.customInfoLicense.text = it.name
                binding.customInfoLicense.show()
            } ?: binding.customInfoLicense.gone()

            Glide
                .with(binding.root)
                .load(item.owner.avatarUrl)
                .into(binding.authorImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowRepositoryItemBinding = RowRepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun replaceItems(newItems: List<GitRepo>) {
        dataSet.clear()
        dataSet.addAll(newItems)
        notifyItemRangeChanged(0, newItems.size)
    }

    fun addNewPageItems(newItems: List<GitRepo>) {
        val dataSetSize = dataSet.size
        //calculate the new items
        val changedItemsCount = abs(newItems.size - dataSetSize)
        val currentPosition = if (dataSetSize == 0) 0 else dataSetSize - 1
        dataSet.clear()
        dataSet.addAll(newItems)
        //notify just the change to the new items, so the adapter dont refresh every item
        notifyItemRangeInserted(currentPosition, changedItemsCount)
    }

}
