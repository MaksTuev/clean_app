package com.two_man.setmaster.ui.screen.profile.condition;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.condition.Condition;

import java.util.ArrayList;

/**
 *
 */
public class ConditionSetPagerAdapter extends PagerAdapter {

    private ArrayList<ConditionSet> conditionSets;
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

    public void showConditionSets(ArrayList<ConditionSet> conditionSets){
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
        ConditionSetView conditionSetView = (ConditionSetView)LayoutInflater
                .from(container.getContext())
                .inflate(R.layout.condition_set_view, container, false);
        conditionSetView.showConditionSet(conditionSets.get(position));
        conditionSetView.setOnConditionActionListener(innerOnConditionActionListener);
        container.addView(conditionSetView);
        return conditionSetView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
}
