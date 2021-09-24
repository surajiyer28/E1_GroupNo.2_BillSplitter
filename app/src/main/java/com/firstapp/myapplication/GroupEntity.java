package com.firstapp.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class GroupEntity {
    GroupEntity(String gName) {
        this.gName = gName;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "GroupName")
    public String gName;

    @ColumnInfo(name = "GroupCurrency")
    public String gCurrency;
}