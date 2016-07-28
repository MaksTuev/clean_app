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
