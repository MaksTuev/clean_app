package com.agna.setmaster.ui.util;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.agna.setmaster.R;

/**
 *
 */
public class StatusBarUtil {
    public static void changeColor(AppCompatActivity activity, boolean active) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int colorRes = active
                    ? R.color.status_bar_active
                    : R.color.status_bar_inactive;
            Window window = activity.getWindow();
            int color = ContextCompat.getColor(activity, colorRes);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}
