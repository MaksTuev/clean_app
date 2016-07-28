package com.agna.setmaster.ui.screen.profile.setting.change;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.setting.ValuableSetting;
import com.agna.setmaster.ui.base.dialog.BaseDialog;
import com.agna.setmaster.ui.util.SettingViewUtil;

/**
 *
 */
public class ChangeVolumeSettingDialog extends BaseDialog {

    public static final String EXTRA_SETTING = "EXTRA_SETTING";
    private static final String EXTRA_ACCENT_COLOR = "EXTRA_ACCENT_COLOR";

    private ValuableSetting setting;
    @ColorInt
    private int accentColor;

    private ImageView deleteBtn;
    private ImageView settingIcon;
    private TextView settingName;
    private AppCompatSeekBar settingValue;

    @Override
    public String getName() {
        return "ChangeVolumeSettingDialog";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting = (ValuableSetting) getArguments().getSerializable(EXTRA_SETTING);
        accentColor = getArguments().getInt(EXTRA_ACCENT_COLOR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_volume_setting_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        initViews();
        initListeners();
    }

    private void initListeners() {
        deleteBtn.setOnClickListener(v -> {
            dismiss();
            getListener(OnSettingChangeListener.class).onSettingDeleted(setting);
        });
        settingValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setting.setValue(seekBar.getProgress() / 100.0f);
                getListener(OnSettingChangeListener.class).onSettingChanged(setting);
            }
        });
    }

    private void initViews() {
        settingIcon.setImageResource(SettingViewUtil.getSettingImage(setting.getClass()));
        settingIcon.setColorFilter(accentColor);
        deleteBtn.setColorFilter(ContextCompat.getColor(getActivity(), R.color.light_gray));
        settingName.setText(SettingViewUtil.getSettingName(setting.getClass()));

        settingValue.setProgress((int) (setting.getValue() * 100));
        settingValue.getProgressDrawable().setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);
        settingValue.getThumb().setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settingValue.setThumbTintList(ColorStateList.valueOf(accentColor));
        }
    }

    private void findView(View view) {
        deleteBtn = (ImageView) view.findViewById(R.id.setting_delete);
        settingIcon = (ImageView) view.findViewById(R.id.setting_icon);
        settingName = (TextView) view.findViewById(R.id.setting_name);
        settingValue = (AppCompatSeekBar) view.findViewById(R.id.setting_value);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new ChangeSettingDialog(getActivity(), getTheme());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public static <S extends ValuableSetting> ChangeVolumeSettingDialog newInstance(S settings, @ColorInt int accentColor) {
        ChangeVolumeSettingDialog dialog = new ChangeVolumeSettingDialog();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SETTING, settings);
        args.putInt(EXTRA_ACCENT_COLOR, accentColor);
        dialog.setArguments(args);
        return dialog;
    }
}
