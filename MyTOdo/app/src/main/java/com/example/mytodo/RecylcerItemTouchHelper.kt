package com.example.mytodo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.Adapter.TodoAdapter

class RecylcerItemTouchHelper(private val adapter: TodoAdapter?) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (adapter == null) return

        if (direction == ItemTouchHelper.LEFT) {
            val builder = AlertDialog.Builder(adapter.context)
            builder.setTitle("Delete task")
            builder.setMessage("Are you sure you want to delete this task?")
            builder.setPositiveButton("Confirm") { _: DialogInterface, _: Int ->
                adapter.deleteItem(position)
            }
            builder.setNegativeButton(android.R.string.cancel) { dialogInterface: DialogInterface, _: Int ->
                adapter.notifyItemChanged(viewHolder.adapterPosition)
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            adapter.editItem(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dx: Float,
        dy: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive)

        if (adapter == null) return

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20
        val iconMargin = (itemView.height) / 4 // adjust as needed
        val iconTop = itemView.top + (itemView.height - 64) / 2 // 64 is a placeholder icon size
        val iconBottom = iconTop + 64

        if (dx > 0) {
            // Swiping to the right (Edit)
            val icon: Drawable? = ContextCompat.getDrawable(adapter.context, R.drawable.baseline_edit)
            val background = ColorDrawable(ContextCompat.getColor(adapter.context, R.color.pastel_blue))

            val iconWidth = icon?.intrinsicWidth ?: 0
            val iconLeft = itemView.left + iconMargin
            val iconRight = iconLeft + iconWidth

            // Draw background first
            background.setBounds(
                itemView.left,
                itemView.top,
                (itemView.left + dx.toInt() + backgroundCornerOffset),
                itemView.bottom
            )
            background.draw(c)

            // Draw icon if not null
            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            icon?.draw(c)

        } else if (dx < 0) {
            // Swiping to the left (Delete)
            val icon: Drawable? = ContextCompat.getDrawable(adapter.context, R.drawable.baseline_delete_24)
            val background = ColorDrawable(Color.RED)

            val iconWidth = icon?.intrinsicWidth ?: 0
            val iconLeft = itemView.right - iconMargin - iconWidth
            val iconRight = itemView.right - iconMargin

            // Draw background first
            background.setBounds(
                (itemView.right + dx.toInt() - backgroundCornerOffset),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            background.draw(c)

            // Draw icon if not null
            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            icon?.draw(c)

        } else {
            // No swipe
            val background = ColorDrawable(Color.TRANSPARENT)
            background.setBounds(0, 0, 0, 0)
            background.draw(c)
        }
    }
}
