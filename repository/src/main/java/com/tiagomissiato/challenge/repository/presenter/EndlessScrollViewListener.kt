package com.tiagomissiato.challenge.repository.presenter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class EndlessScrollViewListener(val onLoad: (page: Int, totalCount: Int) -> Unit): RecyclerView.OnScrollListener() {

    private val visibleThreshold = 5
    private val totalPerPage = 10
    private var currentPage = 1
    private var layoutManager: LayoutManager? = null
    private var previousTotalItemCount = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (layoutManager == null)
            layoutManager = recyclerView.layoutManager

        val lastVisibleItemPosition =
            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        val totalItemCount = layoutManager?.itemCount ?: 0

        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage = (totalItemCount/totalPerPage) + 1
            onLoad(currentPage, totalItemCount)
            isLoading = true
        }
    }

    fun reset() {
        currentPage = 1
        previousTotalItemCount = 0
        isLoading = true
    }

}