package com.agna.setmaster.ui.screen.profile.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.entity.setting.MediaVolumeSetting;
import com.agna.setmaster.entity.setting.RingSetting;
import com.agna.setmaster.entity.setting.Setting;
import com.agna.setmaster.ui.screen.profile.setting.holder.AddSettingViewHolder;
import com.agna.setmaster.ui.screen.profile.setting.holder.MediaVolumeSettingHolder;
import com.agna.setmaster.ui.screen.profile.setting.holder.RingSettingHolder;

import java.util.ArrayList;

/**
 *
 */
public class SettingGridAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_ADD = -1;
    private ArrayList<Setting> settings = new ArrayList<>();
    private OnAddSettingClickListener onAddSettingClickListener;
    private OnSettingClickListener onSettingClickListener;
    private boolean allowAddsetting;

    public SettingGridAdapter(RecyclerView recyclerView) {
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }

    public void setOnAddSettingClickListener(OnAddSettingClickListener onAddSettingClickListener) {
        this.onAddSettingClickListener = onAddSettingClickListener;
    }

    public void setOnSettingClickListener(OnSettingClickListener onSettingClickListener) {
        this.onSettingClickListener = onSettingClickListener;
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new SettingGridLayoutManager(
                recyclerView.getContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void showSettings(ArrayList<Setting> settings, boolean allowAddSetting) {
        this.settings = settings;
        this.allowAddsetting = allowAddSetting;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ADD) {
            return AddSettingViewHolder.newInstance(parent, () -> onAddSettingClickListener.onClick());
        } else {
            for (Setting setting : settings) {
                if (setting.getClass().hashCode() == viewType) {
                    return getSettingHolder(parent, setting);
                }
            }
        }
        throw new IllegalArgumentException("Unsupported viewType: " + viewType);
    }

    private RecyclerView.ViewHolder getSettingHolder(ViewGroup parent, Setting setting) {
        if (setting instanceof RingSetting) {
            return RingSettingHolder.newInstance(parent, this::onSettingHolderClick);
        } else if (setting instanceof MediaVolumeSetting) {
            return MediaVolumeSettingHolder.newInstance(parent, this::onSettingHolderClick);
        } else {
            throw new IllegalArgumentException("Setting " + setting.getClass().getSimpleName() + "not supported");
        }
    }

    private void onSettingHolderClick(View view, int position) {
        onSettingClickListener.onClick(settings.get(position), 0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddSettingViewHolder) {
            return;
        } else if (holder instanceof RingSettingHolder) {
            ((RingSettingHolder) holder).bind((RingSetting) settings.get(position));
        } else if (holder instanceof MediaVolumeSettingHolder) {
            ((MediaVolumeSettingHolder) holder).bind((MediaVolumeSetting) settings.get(position));
        } else {
            throw new IllegalArgumentException("Unsupported holder " + holder.getClass().getSimpleName());
        }

    }

    @Override
    public int getItemCount() {
        return settings.size() + (allowAddsetting ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == settings.size()) {
            return ITEM_TYPE_ADD;
        } else {
            Setting setting = settings.get(position);
            return setting.getClass().hashCode();
        }
    }

    public interface OnSettingHolderClickListener {
        void onClick(View container, int position);
    }

    public interface OnSettingClickListener {
        void onClick(Setting setting, int yCenterPosition);
    }

    public interface OnAddSettingClickListener {
        void onClick();
    }
}
