package com.alex.exam.AppUtils;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Song;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppUtils {

    public static List<Song> sortSongsByAlbumAndTitle() {
        List<Song> thisSongs = ActivityMain.songs;
        Collections.sort(thisSongs, new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                int result = s1.getTitle().compareTo(s2.getTitle());
                if (result == 0)
                    return s1.getAlbum().compareTo(s2.getAlbum());
                return result;
            }
        });
        return thisSongs;
    }

}
