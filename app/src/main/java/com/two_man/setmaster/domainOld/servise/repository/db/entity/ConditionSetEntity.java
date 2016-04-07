package com.two_man.setmaster.domainOld.servise.repository.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.two_man.setmaster.domainOld.servise.repository.db.entity.condition.ConditionEntity;

import java.util.List;

/**
 *
 */
@DatabaseTable(tableName = "conditionSet")
public class ConditionSetEntity {

    @DatabaseField(id = true)
    private int id;

    @ForeignCollectionField(eager = true)
    private List<ConditionEntity> conditions;

    @DatabaseField(foreign = true, canBeNull = false)
    private ProfileEntity profile;
}
