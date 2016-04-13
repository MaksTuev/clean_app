package com.two_man.setmaster.ui.screen.profile.condition;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.ConditionSet;
import com.two_man.setmaster.entity.condition.Condition;

/**
 *
 */
public class ConditionSetView extends FrameLayout {

    private ConditionSet conditionSet;
    private RecyclerView conditionList;
    private ConditionListAdapter adapter;
    private OnConditionActionListener onConditionActionListener;

    public ConditionSetView(Context context) {
        super(context);
        initView(context);
    }

    public ConditionSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ConditionSetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ConditionSetView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void setOnConditionActionListener(OnConditionActionListener onConditionActionListener) {
        this.onConditionActionListener = onConditionActionListener;
    }

    void initView(Context context){
        inflate(context);
        initList();
    }

    public void showConditionSet(ConditionSet conditionSet){
        this.conditionSet = conditionSet;
        adapter.showConditions(conditionSet.getConditions());
    }

    private void initList() {
        adapter = new ConditionListAdapter(conditionList);
        adapter.setOnConditionActionListener(new ConditionListAdapter.OnConditionActionListener() {
            @Override
            public void onClick(Condition condition) {
                onConditionActionListener.onClick(conditionSet.getId(), condition);
            }

            @Override
            public void onDelete(Condition condition) {
                onConditionActionListener.onDelete(conditionSet.getId(), condition);
            }
        });
    }

    private void inflate(Context context) {
        inflate(context, R.layout.condition_set_view_layout, this);
        conditionList = (RecyclerView)findViewById(R.id.condition_list);
    }

    public interface OnConditionActionListener{
        void onClick(String conditionSetId, Condition condition);
        void onDelete(String conditionSetId, Condition condition);
    }
}
