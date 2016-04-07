package com.two_man.setmaster.domainOld.servise.repository.db.entity.condition;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.two_man.setmaster.domainOld.servise.repository.db.entity.ConditionSetEntity;

/**
 *
 */
@DatabaseTable(tableName = "condition")
public class ConditionEntity {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private ConditionType type;

    @DatabaseField(canBeNull = false)
    private String jsonValue;

    @DatabaseField(foreign = true, canBeNull = false)
    private ConditionSetEntity conditionSetEntity;
}
