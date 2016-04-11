package com.two_man.setmaster.ui.screen.editprofile;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.two_man.setmaster.R;

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
        return icons.get(selectedIconPosition);
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
        onItemSelectedListener.onItemSelected(icons.get(selectedIconPosition));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        boolean selected = position == selectedIconPosition;
        ((IconHolder)holder).bind(icons.get(position), position, selected);
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIconPosition = selectedIcon;
        onItemSelectedListener.onItemSelected(icons.get(selectedIconPosition));
        notifyDataSetChanged();
    }

    private static class IconHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private boolean selected = false;
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
            this.selected = selected;
            icon.setBackgroundResource(selected
                    ? R.drawable.small_white_card
                    : R.drawable.transparent);
            icon.setColorFilter(selected
                    ? selectedIconColor
                    : 0XFFFFFFFF);
        }

        public static IconHolder newInstance(ViewGroup parent, OnClickListener onClickListener, int selectedIconColor) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_grid_item, parent, false);
            return new IconHolder(v, onClickListener, selectedIconColor);
        }

        interface OnClickListener{
            void onClick(IconHolder holder);
        }

    }

    public interface OnItemSelected {
        void onItemSelected(int resId);
    }
}
