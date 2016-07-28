package com.agna.setmaster.ui.screen.profile.setting;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 *
 */
public class SettingGridLayoutManager extends GridLayoutManager {

    public SettingGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SettingGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public SettingGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
