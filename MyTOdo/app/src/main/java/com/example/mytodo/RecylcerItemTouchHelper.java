package com.example.mytodo;

import android.app.AlertDialog;
import android.app.BackgroundServiceStartNotAllowedException;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Telephony;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytodo.Adapter.TodoAdapter;

public class RecylcerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private TodoAdapter adapter;
    public RecylcerItemTouchHelper( TodoAdapter adapter){
       super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
       this.adapter=adapter;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        final int position= viewHolder.getAdapterPosition();
        if (direction== ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("delete task");
            builder.setMessage("are yous ure u wanna delete this task? O-O ");
            builder.setPositiveButton("confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           adapter.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                }
            });
            AlertDialog dialog = builder.create();;
            dialog.show();
        }
        else {
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dx, float dy, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        Drawable icon;
        ColorDrawable background;
        int backgroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - 64) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - 64) / 2;
        int iconBottom = iconTop + 64;

        if (dx > 0) {
            // swiping to the right
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.pastel_blue));

            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft + (icon != null ? icon.getIntrinsicWidth() : 64);
            if (icon != null) icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dx) + backgroundCornerOffset,
                    itemView.getBottom());

            background.draw(c);
            if (icon != null) icon.draw(c);

        } else if (dx < 0) {
            // swipes to the left
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_delete_24);
            background = new ColorDrawable(Color.RED);

            int iconLeft = itemView.getRight() - iconMargin - (icon != null ? icon.getIntrinsicWidth() : 64);
            int iconRight = itemView.getRight() - iconMargin;
            if (icon != null) icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dx) - backgroundCornerOffset,
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom());

            background.draw(c);
            if (icon != null) icon.draw(c);

        } else {
            // no swipe
            background = new ColorDrawable(Color.TRANSPARENT);
            background.setBounds(0, 0, 0, 0);
            background.draw(c);
        }
    }

}
