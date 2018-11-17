package com.alex.spottingauto.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void addAnnouncement(Announcement announcement);

    @Update
    void updateAnnouncement(Announcement announcement);

    @Query("SELECT * FROM announcements")
    List<Announcement> getAnnouncements();

    @Query("SELECT * FROM announcements WHERE autor = :autor")
    List<Announcement> getMyAnnouncements(String autor);

    @Query("DELETE FROM announcements")
    void nukeTable();
}
