package com.tony.adlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.WindowManager;

/**
 * Created by dev on 5/11/18.
 */

public class ScrollImageView extends AppCompatImageView {

    private float height,width;
    private int mDx;
    private int mMinDx;

    public ScrollImageView(Context context) {
        super(context,null);
    }

    public ScrollImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        width = wm.getDefaultDisplay().getWidth();
    }

    public ScrollImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
    }

    public void setDx(int dx) {
        if (getDrawable() == null) {
            return;
        }
        mDx = dx;
        if (mDx <= 0) {
            mDx = 0;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
    }

    public int getDx() {
        return mDx;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int w = getWidth();
            int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
            drawable.setBounds(0, 0, w, h);
            canvas.save();
            float scale = height / drawable.getIntrinsicHeight();
            canvas.translate((width - w * scale) / 2, -getDx());
            canvas.scale(height / drawable.getIntrinsicHeight(), height / drawable.getIntrinsicHeight());
            super.onDraw(canvas);
            canvas.restore();
        }
    }
}
