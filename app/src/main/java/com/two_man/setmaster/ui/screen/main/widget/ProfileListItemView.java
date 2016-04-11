package com.two_man.setmaster.ui.screen.main.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;

/**
 *
 */
public class ProfileListItemView extends RelativeLayout {
    private ImageView profileIcon;
    private View profileActiveIndicator;
    private TextView profileNameText;
    private TextView profileStatusText;
    private SettingPreviewLayout profileSettingPreviewLayout;

    private Profile profile;
    private OnProfileActionListener onProfileActionListener;

    public ProfileListItemView(Context context) {
        super(context);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ProfileListItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    protected void initView(Context context) {
        inflate(context);
        findChildViews(context);
    }

    public void show(Profile profile){
        this.profile = profile;
        profileIcon.setImageResource(profile.getImageResId());
        profileActiveIndicator.setVisibility(profile.isActive() ? VISIBLE : GONE);
        profileNameText.setText(profile.getName());
        profileStatusText.setText(getProfileStatusText(profile));
        profileSettingPreviewLayout.showSettings(profile.getSettings());
    }

    public void setListener(OnProfileActionListener onProfileActionListener){
        this.onProfileActionListener = onProfileActionListener;
    }

    private int getProfileStatusText(Profile profile) {
        if(profile.isActive()){
            return R.string.profile_status_active;
        } else {
            return R.string.profile_status_inactive;
        }
    }

    private void findChildViews(Context context) {
        profileIcon = (ImageView)findViewById(R.id.profile_icon);
        profileActiveIndicator = findViewById(R.id.profile_active_indicator);
        profileNameText = (TextView)findViewById(R.id.profile_name_text);
        profileStatusText = (TextView)findViewById(R.id.profile_status_text);
        profileSettingPreviewLayout = (SettingPreviewLayout)findViewById(R.id.profile_setting_preview);
    }


    private void inflate(Context context) {
        inflate(context, R.layout.profile_list_item, this);
    }

    public interface OnProfileActionListener {
    }
}
