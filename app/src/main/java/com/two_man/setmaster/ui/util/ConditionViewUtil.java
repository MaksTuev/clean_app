package com.two_man.setmaster.ui.util;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.condition.Condition;
import com.two_man.setmaster.entity.condition.TimeCondition;
import com.two_man.setmaster.entity.condition.WiFiCondition;

/**
 *
 */
public class ConditionViewUtil {
    public static int getConditionImage(Class<? extends Condition> conditionClass) {
        if(conditionClass == TimeCondition.class){
            return R.drawable.ic_cond_time;
        } else if(conditionClass == WiFiCondition.class){
            return R.drawable.ic_cond_wifi;
        } else{
            throw new IllegalArgumentException("Unsupported condition "+ conditionClass.getSimpleName());
        }
    }

    public static int getConditionName(Class<? extends Condition> conditionClass) {
        if(conditionClass == TimeCondition.class){
            return R.string.condition_name_time;
        } else if(conditionClass == WiFiCondition.class){
            return R.string.condition_name_wifi;
        } else{
            throw new IllegalArgumentException("Unsupported condition "+ conditionClass.getSimpleName());
        }
    }
}
