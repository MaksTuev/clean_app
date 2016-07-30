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
package com.agna.setmaster.ui.screen.profile.condition.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.ui.screen.profile.condition.ConditionListAdapter;
import com.agna.setmaster.ui.util.ConditionViewUtil;

/**
 *
 */
public class WiFiConditionHolder extends RecyclerView.ViewHolder implements ConditionHolder<WiFiCondition> {

    protected final TextView value;
    protected final ImageView icon;
    private final View container;
    private final TextView status;
    private final View deleteBtn;

    public WiFiConditionHolder(View itemView, ConditionListAdapter.OnConditionHolderActionListener listener) {
        super(itemView);
        container = itemView.findViewById(R.id.condition_container);
        icon = (ImageView) itemView.findViewById(R.id.condition_icon);
        value = (TextView) itemView.findViewById(R.id.condition_value);
        status = (TextView) itemView.findViewById(R.id.condition_status);
        deleteBtn = itemView.findViewById(R.id.condition_delete);
        deleteBtn.setOnClickListener(v -> listener.onDelete(getAdapterPosition()));
        container.setOnClickListener(v -> listener.onClick(v, getAdapterPosition()));
    }

    public void bind(WiFiCondition condition) {
        icon.setImageResource(ConditionViewUtil.getConditionImage(condition.getClass()));
        value.setText(condition.getNetworkName());
        status.setVisibility(condition.isActive()
                ? View.VISIBLE
                : View.GONE);
    }

    public static WiFiConditionHolder newInstance(ViewGroup parent, ConditionListAdapter.OnConditionHolderActionListener listener) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.codition_list_item_layout, parent, false);
        return new WiFiConditionHolder(v, listener);
    }
}
