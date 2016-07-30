/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.ui.screen.condition.wifi;

import android.content.res.ColorStateList;
import android.net.wifi.WifiConfiguration;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.R;

import java.util.ArrayList;

/**
 *
 */
public class WifiNetworkAdapter extends RecyclerView.Adapter {
    ArrayList<WifiConfiguration> wifiNetworks = new ArrayList<>();

    private int selectedIconPosition = 0;
    private int accentColor = 0;

    public WifiNetworkAdapter(RecyclerView recyclerView) {
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAccentColor(@ColorInt int AccentColor) {
        this.accentColor = AccentColor;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NetworkHolder.newInstance(parent, this::onItemClick, accentColor);

    }

    private void onItemClick(NetworkHolder holder) {
        selectedIconPosition = holder.getAdapterPosition();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean selected = position == selectedIconPosition;
        ((NetworkHolder) holder).bind(wifiNetworks.get(position), selected);
    }

    @Override
    public int getItemCount() {
        return wifiNetworks.size();
    }

    public void setSelectedNetwork(String networkName) {
        for (int i = 0; i < wifiNetworks.size(); i++) {
            if (wifiNetworks.get(i).SSID.equals(networkName)) {
                this.selectedIconPosition = i;
            }
        }
        notifyDataSetChanged();
    }

    public WifiConfiguration getSelectedNetwork() {
        return wifiNetworks.get(selectedIconPosition);
    }

    public void showNetworks(ArrayList<WifiConfiguration> wifiNetworks) {
        this.wifiNetworks = wifiNetworks;
        notifyDataSetChanged();
    }

    private static class NetworkHolder extends RecyclerView.ViewHolder {

        private final AppCompatRadioButton name;
        private int colorAccent;
        private boolean selected;

        public NetworkHolder(View itemView, OnClickListener onClickListener, @ColorInt int colorAccent) {
            super(itemView);
            this.colorAccent = colorAccent;
            name = (AppCompatRadioButton) itemView.findViewById(R.id.network_rb);
            name.setSupportButtonTintList(ColorStateList.valueOf(colorAccent));
            name.setOnClickListener(v -> {
                onClickListener.onClick(this);
            });
        }

        public void bind(WifiConfiguration networkConfiguration, boolean selected) {
            name.setText(networkConfiguration.SSID);
            setSelected(selected);
        }

        private void setSelected(boolean selected) {
            name.setChecked(selected);
        }

        public static NetworkHolder newInstance(ViewGroup parent, OnClickListener onClickListener, int accentColor) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_list_item, parent, false);
            return new NetworkHolder(v, onClickListener, accentColor);
        }

        interface OnClickListener {
            void onClick(NetworkHolder holder);
        }

    }
}
