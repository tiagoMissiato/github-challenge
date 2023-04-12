package com.tiagomissiato.challenge.repository.presenter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tiagomissiato.challenge.core.extensions.hide
import com.tiagomissiato.challenge.core.extensions.launchOnStarted
import com.tiagomissiato.challenge.core.extensions.show
import com.tiagomissiato.challenge.repository.R
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.databinding.FragmentRepositoryListBinding
import com.tiagomissiato.challenge.repository.presenter.adapter.RecyclerViewMargin
import com.tiagomissiato.challenge.repository.presenter.adapter.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryListFragment : Fragment() {

    private val viewModel: RepositoryListViewModel by viewModels()

    private var _binding: FragmentRepositoryListBinding? = null

    private val endlessScrollViewListener = EndlessScrollViewListener { page, total ->
        viewModel.getPage(page)
    }

    private val itemsAdapter by lazy {
        RepositoryListAdapter(mutableListOf())
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getRefreshGitRepoList()
            endlessScrollViewListener.reset()
        }

        binding.recyclerView.adapter = itemsAdapter

        val margin = resources.getDimension(R.dimen.item_row_horizontal_margin).toInt()
        binding.recyclerView.addItemDecoration(RecyclerViewMargin(margin = margin))

        binding.recyclerView.addOnScrollListener(endlessScrollViewListener)

        launchOnStarted {
            viewModel.uiState.collect {
                when(it) {
                    RepositoryListUiState.Loading -> {
                        binding.containerLoadingRepositoryList.viewLoadingRepositoryList.show()
                        binding.containerEmptyRepositoryList.viewEmptyRepositoryList.hide()
                    }
                    RepositoryListUiState.LoadingMore -> {
                        binding.containerLoadingMoreRepositoryList.viewLoadingMoreRepositoryList.show()
                        binding.containerEmptyRepositoryList.viewEmptyRepositoryList.hide()
                    }
                    RepositoryListUiState.Empty -> {
                        binding.containerEmptyRepositoryList.viewEmptyRepositoryList.show()
                        binding.containerLoadingRepositoryList.viewLoadingRepositoryList.hide()
                        binding.swipeToRefresh.isRefreshing = false
                    }
                    is RepositoryListUiState.Success -> {
                        binding.warningMessage.visibility = View.GONE
                        showList(it.items.toMutableList())
                    }
                    is RepositoryListUiState.LoadMore -> {
                        binding.warningMessage.visibility = View.GONE
                        showList(it.items.toMutableList())
                    }
                    is RepositoryListUiState.Error -> {
                        binding.warningMessage.text = it.message
                        binding.warningMessage.show()
                        showList(it.cache.toMutableList())
                    }
                    is RepositoryListUiState.Exception -> {
                        binding.warningMessage.text = it.message
                        binding.swipeToRefresh.isRefreshing = false
                        binding.containerEmptyRepositoryList.viewEmptyRepositoryList.hide()
                    }
                }
            }

        }
        viewModel.getPage()

        return root
    }

    private fun showList(list: MutableList<GitRepo>) {
        itemsAdapter.addNewPageItems(list)
        binding.swipeToRefresh.isRefreshing = false
        binding.containerLoadingRepositoryList.viewLoadingRepositoryList.hide()
        binding.containerLoadingMoreRepositoryList.viewLoadingMoreRepositoryList.hide()
        binding.containerEmptyRepositoryList.viewEmptyRepositoryList.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}