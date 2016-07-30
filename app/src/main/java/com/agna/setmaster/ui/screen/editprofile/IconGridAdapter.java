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
package com.agna.setmaster.ui.screen.editprofile;

import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agna.setmaster.R;

import java.util.List;

/**
 *
 */
public class IconGridAdapter extends RecyclerView.Adapter {

    private OnItemSelected onItemSelectedListener;
    private List<Integer> icons;
    private int selectedIconPosition = 0;
    @ColorInt
    private int selectedIconColor;

    @DrawableRes
    public int getSelectedIcon() {
        return selectedIconPosition;
    }

    public IconGridAdapter(RecyclerView recyclerView, List<Integer> icons, OnItemSelected onItemSelectedListener) {
        initLayoutManager(recyclerView);
        this.icons = icons;
        this.onItemSelectedListener = onItemSelectedListener;
        recyclerView.setAdapter(this);
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                recyclerView.getContext(), 3, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setSelectedIconColor(@ColorInt int selectedIconColor) {
        this.selectedIconColor = selectedIconColor;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return IconHolder.newInstance(parent, this::onItemClick, selectedIconColor);
    }

    private void onItemClick(IconHolder holder) {
        selectedIconPosition = holder.getItemPosition();
        onItemSelectedListener.onItemSelected(selectedIconPosition);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean selected = position == selectedIconPosition;
        ((IconHolder) holder).bind(icons.get(position), position, selected);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIconPosition = selectedIcon;
        onItemSelectedListener.onItemSelected(selectedIconPosition);
        notifyDataSetChanged();
    }

    private static class IconHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private int position;
        private int selectedIconColor;

        public IconHolder(View itemView, OnClickListener onClickListener, @ColorInt int selectedIconColor) {
            super(itemView);
            this.selectedIconColor = selectedIconColor;
            icon = (ImageView) itemView.findViewById(R.id.icon);
            icon.setOnClickListener(v -> onClickListener.onClick(this));
        }

        public int getItemPosition() {
            return position;
        }

        public void bind(int iconRes, int position, boolean selected) {
            this.position = position;
            icon.setImageResource(iconRes);
            setSelected(selected);
        }

        private void setSelected(boolean selected) {
            int iconColor = selected
                    ? selectedIconColor
                    : 0xFFFFFF;
            icon.getDrawable().setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
            icon.setBackgroundResource(selected
                    ? R.drawable.small_white_card
                    : R.drawable.transparent);
        }

        public static IconHolder newInstance(ViewGroup parent, OnClickListener onClickListener, int selectedIconColor) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_grid_item, parent, false);
            return new IconHolder(v, onClickListener, selectedIconColor);
        }

        interface OnClickListener {
            void onClick(IconHolder holder);
        }

    }

    public interface OnItemSelected {
        void onItemSelected(int iconId);
    }
}
