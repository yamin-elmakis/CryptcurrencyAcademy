package dev.yamin.cryptcurrencyacademy.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Yamin on 3/16/2018.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    // the sizes in PX
    private int left, right, top, bottom;
    private int firstTop, firstStart;
    private int lastBottom, lastEnd;

    public SpacesItemDecoration(int space) {
        this(space, space, space, space);
    }

    public SpacesItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        boolean rtl = false;
        if (view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            rtl = true;
        }

        int position = parent.getChildAdapterPosition(view);
        if (firstTop > 0 && position == 0) {
            outRect.top = top + firstTop;
        } else
            outRect.top = top;

        if (lastBottom > 0 && position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = bottom + lastBottom;
        } else
            outRect.bottom = bottom;

        outRect.left = left;
        outRect.right = right;
        if (firstStart > 0 && position == 0) {
            if (rtl)
                outRect.right += firstStart;
            else
                outRect.left += firstStart;
        }

        if (lastEnd > 0 && position == parent.getAdapter().getItemCount() - 1) {
            if (rtl)
                outRect.left += lastEnd;
            else
                outRect.right += lastEnd;
        }
    }

    public void setFirstTop(int firstTop) {
        this.firstTop = firstTop;
    }

    public void setFirstStart(int firstStart) {
        this.firstStart = firstStart;
    }

    public void setLastBottom(int lastBottom) {
        this.lastBottom = lastBottom;
    }

    public void setLastEnd(int lastEnd) {
        this.lastEnd = lastEnd;
    }
}
