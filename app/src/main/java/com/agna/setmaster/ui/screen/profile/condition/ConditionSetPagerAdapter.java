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

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.ConditionSet;
import com.agna.setmaster.domain.condition.Condition;

import java.util.ArrayList;

/**
 *
 */
public class ConditionSetPagerAdapter extends PagerAdapter {

    private ArrayList<ConditionSet> conditionSets = new ArrayList<>();
    private ConditionSetView.OnConditionActionListener innerOnConditionActionListener;
    private ConditionSetView.OnConditionActionListener onConditionActionListener;


    public ConditionSetPagerAdapter() {
        innerOnConditionActionListener = new ConditionSetView.OnConditionActionListener() {
            @Override
            public void onClick(String conditionSetId, Condition condition) {
                onConditionActionListener.onClick(conditionSetId, condition);
            }

            @Override
            public void onDelete(String conditionSetId, Condition condition) {
                onConditionActionListener.onDelete(conditionSetId, condition);
            }
        };
    }

    public void showConditionSets(ArrayList<ConditionSet> conditionSets) {
        this.conditionSets = conditionSets;
        notifyDataSetChanged();
    }

    public void setOnConditionActionListener(ConditionSetView.OnConditionActionListener onConditionActionListener) {
        this.onConditionActionListener = onConditionActionListener;
    }

    @Override
    public int getCount() {
        return conditionSets.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ConditionSetView conditionSetView = (ConditionSetView) LayoutInflater
                .from(container.getContext())
                .inflate(R.layout.condition_set_view, container, false);
        conditionSetView.showConditionSet(conditionSets.get(position));
        conditionSetView.setOnConditionActionListener(innerOnConditionActionListener);
        container.addView(conditionSetView);
        return conditionSetView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    public String getConditionSetId(int currentItem) {
        return conditionSets.get(currentItem).getId();
    }
}
