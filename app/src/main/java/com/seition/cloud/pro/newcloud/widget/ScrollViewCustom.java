package com.seition.cloud.pro.newcloud.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by xzw on 2017/8/9.
 */

public class ScrollViewCustom extends HorizontalListView {
    private Runnable scrollerTask;
    private int intitPosition;
    private int newCheck = 100;
    private int childWidth = 0;

    public interface OnScrollStopListner {
        /**
         * scroll have stoped
         */
        void onScrollStoped();

        /**
         * scroll have stoped, and is at left edge
         */
        void onScrollToLeftEdge();

        /**
         * scroll have stoped, and is at right edge
         */
        void onScrollToRightEdge();

        /**
         * scroll have stoped, and is at middle
         */
        void onScrollToMiddle();
    }

    private OnScrollStopListner onScrollstopListner;

    public ScrollViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollerTask = new Runnable() {
            @Override
            public void run() {
                int newPosition = getScrollX();
                if (intitPosition - newPosition == 0) {
                    if (onScrollstopListner == null) {
                        return;
                    }
                    onScrollstopListner.onScrollStoped();
                    Rect outRect = new Rect();
                    getDrawingRect(outRect);
//                    System.out.println("ScrollViewCustom-hor_list.getScrollX()=" + getScrollX() +
//                            ",outRect.right =" + outRect.right +
//                            ",childWidth =" + childWidth +
//                            ",getPaddingLeft =" + getPaddingLeft() +
//                            ",getPaddingRight =" + getPaddingRight() +
//                            ",getMeasuredWidth =" + getMeasuredWidth() +
//                            ",getMeasuredHeight =" + getMeasuredHeight() +
//                            ",getWidth =" + getWidth() +
//                            "," + outRect);

//                    if (getChildAt(getChildCount() - 1) != null)
//                        System.out.println("ScrollViewCustom - getChildAt(getChildCount()-1).getRight() =" + getChildAt(getChildCount() - 1).getRight());
                    if (getChildAt(0) != null && getChildAt(0).getLeft() == outRect.left && direction.equals(SCROLL_DIRECTION_LEFT)) {
                        onScrollstopListner.onScrollToLeftEdge();
                    } else if (direction.equals(SCROLL_DIRECTION_RIGHT)) {
                        if (getChildAt(getChildCount() - 1) != null && getChildAt(getChildCount() - 1).getRight() == outRect.right) {
                            onScrollstopListner.onScrollToRightEdge();
                        } else if (childWidth < outRect.right) {
                            onScrollstopListner.onScrollToRightEdge();
                        }
                    } else {
                        onScrollstopListner.onScrollToMiddle();
                    }
                } else {
                    intitPosition = getScrollX();
                    postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }


    public void setOnScrollStopListner(OnScrollStopListner listner) {
        onScrollstopListner = listner;
    }

    public static final String SCROLL_DIRECTION_LEFT = "LEFT";
    public static final String SCROLL_DIRECTION_RIGHT = "RIGHT";
    private String direction;

    public void startScrollerTask(String direction) {
        this.direction = direction;
        intitPosition = getScrollX();
        postDelayed(scrollerTask, newCheck);
        checkTotalWidth();
    }

    private void checkTotalWidth() {
        if (childWidth > 0) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            childWidth += getChildAt(i).getWidth();
        }
    }
}
