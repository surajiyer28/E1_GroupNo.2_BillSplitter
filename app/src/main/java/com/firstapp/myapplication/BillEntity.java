package com.firstapp.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// Column "MemberId" has a foreign key reference to column "Id" of MemberEntity.
// Column "GroupName" has a foreign key reference to column "GroupName" of GroupEntity
@Entity(foreignKeys = {
        @ForeignKey(entity = MemberEntity.class,
            parentColumns = "Id",
            childColumns = "MemberId",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(entity = GroupEntity.class,
            parentColumns = "GroupName",
            childColumns = "GroupName",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )},
        indices = {
                @Index(name="MemberIdIndex",value = {"MemberId"}),
                @Index(name="GroupNameIndexBill",value = {"GroupName"})
        })
public class BillEntity {


    BillEntity(int mid, String item, String cost, String gName, String paidBy, byte[] image, String location) {
        this.image = image;
        this.mid = mid;
        this.item = item;
        this.cost = cost;
        this.gName = gName;
        this.paidBy = paidBy;
        this.location = location;


    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public int id;

    @ColumnInfo(name = "MemberId") // id of the member who paid for the bill
    public int mid;

    @ColumnInfo(name = "Item")
    public String item;

    @ColumnInfo(name = "PaidBy") // name of the member who paid for the bill
    String paidBy;

    @ColumnInfo(name = "Cost")
    String cost;

    @ColumnInfo(name = "GroupName")
    String gName;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    byte [] image;

    @ColumnInfo(name = "Location")
    public String location;


    public void setId(int id) {
        this.id = id;
    }
}