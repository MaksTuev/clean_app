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
package com.agna.setmaster.ui.screen.profile.condition;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.domain.condition.Condition;
import com.agna.setmaster.domain.condition.TimeCondition;
import com.agna.setmaster.domain.condition.WiFiCondition;
import com.agna.setmaster.ui.screen.profile.condition.holder.ConditionHolder;
import com.agna.setmaster.ui.screen.profile.condition.holder.TimeConditionHolder;
import com.agna.setmaster.ui.screen.profile.condition.holder.WiFiConditionHolder;

import java.util.ArrayList;

/**
 *
 */
public class ConditionListAdapter extends RecyclerView.Adapter {
    private ArrayList<Condition> conditions = new ArrayList<>();
    private OnConditionActionListener onConditionActionListener;

    public ConditionListAdapter(RecyclerView recyclerView) {
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }

    public void setOnConditionActionListener(OnConditionActionListener onConditionActionListener) {
        this.onConditionActionListener = onConditionActionListener;
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void showConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (Condition condition : conditions) {
            if (condition.getClass().hashCode() == viewType) {
                return getConditionHolder(parent, condition);
            }
        }
        throw new IllegalArgumentException("Unsupported viewType: " + viewType);
    }

    private RecyclerView.ViewHolder getConditionHolder(ViewGroup parent, Condition condition) {
        if (condition instanceof TimeCondition) {
            return TimeConditionHolder.newInstance(parent, onConditionHolderActionListener);
        } else if (condition instanceof WiFiCondition) {
            return WiFiConditionHolder.newInstance(parent, onConditionHolderActionListener);
        } else {
            throw new IllegalArgumentException("Condition " + condition.getClass().getSimpleName() + "not supported");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConditionHolder) holder).bind(conditions.get(position));
    }

    private OnConditionHolderActionListener onConditionHolderActionListener = new OnConditionHolderActionListener() {
        @Override
        public void onClick(View container, int position) {
            onConditionActionListener.onClick(conditions.get(position));
        }

        @Override
        public void onDelete(int position) {
            onConditionActionListener.onDelete(conditions.get(position));
        }
    };

    @Override
    public int getItemCount() {
        return conditions.size();
    }

    @Override
    public int getItemViewType(int position) {
        Condition condition = conditions.get(position);
        return condition.getClass().hashCode();
    }

    public interface OnConditionHolderActionListener {
        void onClick(View container, int position);

        void onDelete(int position);
    }

    public interface OnConditionActionListener {
        void onClick(Condition condition);

        void onDelete(Condition condition);
    }
}
