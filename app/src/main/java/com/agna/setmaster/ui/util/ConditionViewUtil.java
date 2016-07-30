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
package com.agna.setmaster.ui.util;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.condition.Condition;
import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.entity.condition.WiFiCondition;

/**
 *
 */
public class ConditionViewUtil {
    public static int getConditionImage(Class<? extends Condition> conditionClass) {
        if (conditionClass == TimeCondition.class) {
            return R.drawable.ic_cond_time;
        } else if (conditionClass == WiFiCondition.class) {
            return R.drawable.ic_cond_wifi;
        } else {
            throw new IllegalArgumentException("Unsupported condition " + conditionClass.getSimpleName());
        }
    }

    public static int getConditionName(Class<? extends Condition> conditionClass) {
        if (conditionClass == TimeCondition.class) {
            return R.string.condition_name_time;
        } else if (conditionClass == WiFiCondition.class) {
            return R.string.condition_name_wifi;
        } else {
            throw new IllegalArgumentException("Unsupported condition " + conditionClass.getSimpleName());
        }
    }
}
