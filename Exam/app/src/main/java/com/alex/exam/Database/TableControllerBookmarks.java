package com.alex.exam.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerBookmarks extends DatabaseHandler {

    public TableControllerBookmarks(Context context) {
        super(context);
    }

    public boolean insertBookmark(Bookmark bookmark) {
        ContentValues values = new ContentValues();
        values.put("name", bookmark.getName());
        values.put("description", bookmark.getDescription());
        values.put("url", bookmark.getUrl());
        values.put("type", bookmark.getType());
        values.put("rating", bookmark.getRating());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("bookmarks", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Bookmark> readBookmarksDB() {
        List<Bookmark> productsList = new ArrayList<>();
        String sql = "SELECT * FROM bookmarks ORDER BY ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                int rating = Integer.parseInt(cursor.getString(cursor.getColumnIndex("rating")));

                Bookmark bookmark = new Bookmark(name, description, url, type, rating);
                bookmark.setID(ID);
                productsList.add(bookmark);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productsList;
    }

    public boolean deleteBookmark(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete("bookmarks", "id ='" + ID + "'", null) > 0;
        db.close();
        return deleteSuccessful;
    }

    public boolean updateProduct(Bookmark bookmark) {
        ContentValues values = new ContentValues();
        values.put("name", bookmark.getName());
        values.put("description", bookmark.getDescription());
        values.put("url", bookmark.getUrl());
        values.put("type", bookmark.getType());
        values.put("rating", bookmark.getRating());
        String where = "id = ?";

        String[] whereArgs = {Integer.toString(bookmark.getID())};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSuccessful = db.update("bookmarks", values, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }

    public int countRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM bookmarks";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }
}