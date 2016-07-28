package com.agna.setmaster.ui.screen.profile.setting.holder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class SettingGridItemPreviewProgress extends View {
    private int w;
    private int h;
    private Paint paint = new Paint();
    private float value;

    public SettingGridItemPreviewProgress(Context context) {
        super(context);
    }

    public SettingGridItemPreviewProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingGridItemPreviewProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SettingGridItemPreviewProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setValue(float value) {
        this.value = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x66ffffff);
        paint.setColor(0xffffffff);
        canvas.drawRect(0, 0, w * value, h, paint);
    }
}
