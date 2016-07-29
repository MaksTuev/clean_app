package com.agna.setmaster.ui.screen.profile.setting.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.R;
import com.agna.setmaster.ui.screen.profile.setting.SettingGridAdapter;

/**
 *
 */
public class AddSettingViewHolder extends RecyclerView.ViewHolder {
    public AddSettingViewHolder(View itemView, SettingGridAdapter.OnAddSettingClickListener listener) {
        super(itemView);
        View conatiner = itemView.findViewById(R.id.setting_container);
        conatiner.setOnClickListener(v -> listener.onClick());


    }

    public static AddSettingViewHolder newInstance(ViewGroup parent, SettingGridAdapter.OnAddSettingClickListener listener) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_setting_grid_item_layout, parent, false);
        return new AddSettingViewHolder(v, listener);
    }
}
