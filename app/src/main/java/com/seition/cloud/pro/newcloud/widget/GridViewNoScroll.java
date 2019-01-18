package com.seition.cloud.pro.newcloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewNoScroll extends GridView {

	public GridViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GridViewNoScroll(Context context) {
		super(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
