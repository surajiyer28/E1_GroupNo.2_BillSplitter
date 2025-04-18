package com.firstapp.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MemberEntity.class, GroupEntity.class, BillEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String db_name = "TTMM";
    private static AppDatabase instance;

    // if an instance of the database was already created return it else generate a new instance
    static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, db_name).fallbackToDestructiveMigration().build();
        }
        return instance;
    }


    public abstract MemberDao memberDao();
    public abstract BillDao billDao();
    public abstract GroupDao groupDao();

}