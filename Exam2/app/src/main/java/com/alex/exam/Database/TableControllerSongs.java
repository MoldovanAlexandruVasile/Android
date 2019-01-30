package com.alex.exam.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerSongs extends DatabaseHandler {

    public TableControllerSongs(Context context) {
        super(context);
    }

    public boolean insertSong(Song song) {
        ContentValues values = new ContentValues();
        values.put("title", song.getTitle());
        values.put("description", song.getDescription());
        values.put("album", song.getAlbum());
        values.put("genre", song.getGenre());
        values.put("year", song.getYear());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("songs", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Song> readProductsDB() {
        List<Song> productsList = new ArrayList<>();
        String sql = "SELECT * FROM songs ORDER BY ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                String name = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String quantity = cursor.getString(cursor.getColumnIndex("album"));
                String price = cursor.getString(cursor.getColumnIndex("genre"));
                String status = cursor.getString(cursor.getColumnIndex("year"));
                Song song = new Song(name, description, quantity, price, status);
                song.setID(ID);
                productsList.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productsList;
    }

    public boolean deleteProduct(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete("songs", "id ='" + ID + "'", null) > 0;
        db.close();
        return deleteSuccessful;
    }

    public boolean updateProduct(Song song) {
        ContentValues values = new ContentValues();
        values.put("title", song.getTitle());
        values.put("description", song.getDescription());
        values.put("album", song.getAlbum());
        values.put("genre", song.getGenre());
        values.put("year", song.getYear());
        String where = "id = ?";

        String[] whereArgs = {Integer.toString(song.getID())};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSuccessful = db.update("songs", values, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }

    public int countRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM songs";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }
}