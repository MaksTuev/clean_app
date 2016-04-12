package com.two_man.setmaster.ui.screen.profile.setting.choosesetting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.setting.Setting;
import com.two_man.setmaster.ui.base.dialog.BaseDialog;

import java.util.ArrayList;

/**
 *
 */
public class ChooseSettingDialog extends BaseDialog {

    public static final String EXTRA_SETTINGS = "EXTRA_SETTINGS";
    private SettingListAdapter adapter;

    @Override
    public String getName() {
        return "ChooseSettingDialog";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_setting_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView settingList = (RecyclerView) view.findViewById(R.id.settings_list);
        adapter = new SettingListAdapter(settingList, settingClass -> {
            getListener(OnSettingChosenListener.class).onSettingChosen(settingClass);
            dismiss();
        });
        ArrayList<Class<? extends Setting>> settings = (ArrayList<Class<? extends Setting>>) getArguments().getSerializable(EXTRA_SETTINGS);
        adapter.showSettings(settings);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public interface OnSettingChosenListener {
        void onSettingChosen(Class<? extends Setting> settingClass);
    }

    public static ChooseSettingDialog newInstance(ArrayList<Class<? extends Setting>> settings) {
        ChooseSettingDialog dialog = new ChooseSettingDialog();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SETTINGS, settings);
        dialog.setArguments(args);
        return dialog;
    }
}
