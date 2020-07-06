package com.example.a2nd_seminar.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(private val verticalSpacing: Int): RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // 마지막 아이템
        if (itemCount > 1 && itemPosition == itemCount - 1) {
            outRect.bottom = 20
        }
        // 사이에 있는 모든 아이템
        else {
            outRect.bottom = verticalSpacing
        }
    }
}