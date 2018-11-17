package com.alex.spottingauto.Database;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Announcement.class}, version = 4)
public abstract class Database extends RoomDatabase {
    public abstract DAO DAO();
}
