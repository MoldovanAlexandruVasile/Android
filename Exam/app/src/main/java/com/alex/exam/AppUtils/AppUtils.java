package com.alex.exam.AppUtils;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Bookmark;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppUtils {

    public static List<Bookmark> sortBookmarksByRating() {
        List<Bookmark> thisBookmarks = ActivityMain.underratedBookmarks;
        Collections.sort(thisBookmarks, new Comparator<Bookmark>() {
            @Override
            public int compare(Bookmark p1, Bookmark p2) {
                return p1.getRating().compareTo(p2.getRating());
            }
        });
        return thisBookmarks;
    }
}
