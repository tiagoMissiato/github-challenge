package com.tiagomissiato.challenge.repository.presenter.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewMargin(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val totalItems = parent.adapter?.itemCount ?: 0
        val pos = parent.getChildAdapterPosition(view)

        if (pos < totalItems)
            outRect.bottom = margin

    }

}
