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
package com.agna.setmaster.ui.screen.profile.condition.add;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.ui.util.ConditionViewUtil;

import java.util.ArrayList;

/**
 *
 */
public class AddConditionListAdapter extends RecyclerView.Adapter {
    private OnConditionChosenListener onConditionChosenListener;
    private ArrayList<Class<? extends Condition>> conditions;

    public AddConditionListAdapter(RecyclerView recyclerView, OnConditionChosenListener onConditionChosenListener) {
        this.onConditionChosenListener = onConditionChosenListener;
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ConditionHolder.newInstance(
                parent, position -> onConditionChosenListener.onConditionChosen(conditions.get(position)));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConditionHolder) holder).bindCondition(conditions.get(position));
    }

    @Override
    public int getItemCount() {
        return conditions.size();
    }

    public void showConditions(ArrayList<Class<? extends Condition>> conditions) {
        this.conditions = conditions;
        notifyDataSetChanged();
    }

    public interface OnConditionChosenListener {
        void onConditionChosen(Class<? extends Condition> conditionType);
    }

    private static class ConditionHolder extends RecyclerView.ViewHolder {

        private final OnItemClickListener listener;
        private final ImageView icon;
        private final TextView name;

        public ConditionHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
            icon = (ImageView) itemView.findViewById(R.id.condition_icon);
            name = (TextView) itemView.findViewById(R.id.condition_name);
            icon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.gray));
        }

        public void bindCondition(Class<? extends Condition> conditionClass) {
            icon.setImageResource(ConditionViewUtil.getConditionImage(conditionClass));
            name.setText(ConditionViewUtil.getConditionName(conditionClass));
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public static ConditionHolder newInstance(ViewGroup parent, OnItemClickListener listener) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_condition_list_item_layout, parent, false);
            return new ConditionHolder(v, listener);
        }
    }
}
