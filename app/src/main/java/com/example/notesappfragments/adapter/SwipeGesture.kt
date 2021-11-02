package com.example.notesappfragments.adapter

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.notesappfragments.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeGesture(val context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    val deleteColor = ContextCompat.getColor(context, R.color.deletecolor)
    val updateColor = ContextCompat.getColor(context, R.color.updatecolor)
    val deleteIcon = R.drawable.delete
    val updateIcon = R.drawable.update

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftLabel("Delete")
            .addSwipeLeftActionIcon(deleteIcon)
            .addSwipeRightBackgroundColor(updateColor)
            .addSwipeRightLabel("Update")
            .addSwipeRightActionIcon(updateIcon)
            .create()
            .decorate()
        super.onChildDraw(
            c!!, recyclerView!!,
            viewHolder!!, dX, dY, actionState, isCurrentlyActive
        )
    }
}