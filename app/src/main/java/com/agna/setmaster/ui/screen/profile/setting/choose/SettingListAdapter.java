package com.agna.setmaster.ui.screen.profile.setting.choose;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.ui.util.SettingViewUtil;

import java.util.ArrayList;

/**
 *
 */
public class SettingListAdapter extends RecyclerView.Adapter {

    private ArrayList<Class<? extends Setting>> settings = new ArrayList<>();
    private OnSettingClickListener onSettingClickListener;

    public SettingListAdapter(RecyclerView recyclerView, OnSettingClickListener onSettingClickListener) {
        this.onSettingClickListener = onSettingClickListener;
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SettingHolder.newInstance(
                parent, position -> onSettingClickListener.onSettingClick(settings.get(position)));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SettingHolder) holder).bindSetting(settings.get(position));
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public void showSettings(ArrayList<Class<? extends Setting>> settings) {
        this.settings = settings;
        notifyDataSetChanged();
    }

    public interface OnSettingClickListener {
        void onSettingClick(Class<? extends Setting> settingClass);
    }

    private static class SettingHolder extends RecyclerView.ViewHolder {

        private final OnItemClickListener listener;
        private final ImageView icon;
        private final TextView name;

        public SettingHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
            icon = (ImageView) itemView.findViewById(R.id.setting_icon);
            name = (TextView) itemView.findViewById(R.id.setting_name);
        }

        public void bindSetting(Class<? extends Setting> settingClass) {
            icon.setImageResource(SettingViewUtil.getSettingImage(settingClass));
            name.setText(SettingViewUtil.getSettingName(settingClass));
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public static SettingHolder newInstance(ViewGroup parent, OnItemClickListener listener) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_item_layout, parent, false);
            return new SettingHolder(v, listener);
        }
    }
}
