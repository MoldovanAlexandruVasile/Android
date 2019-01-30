package com.alex.exam.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerProducts extends DatabaseHandler {

    public TableControllerProducts(Context context) {
        super(context);
    }

    public boolean insertProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("quantity", product.getQuantity());
        values.put("price", product.getPrice());
        values.put("status", product.getStatus());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("products", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Product> readProductsDB() {
        List<Product> productsList = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int ID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("quantity")));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                String status = cursor.getString(cursor.getColumnIndex("status"));

                Product product = new Product(name, description, quantity, price, status);
                product.setID(ID);
                productsList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productsList;
    }

    public boolean deleteProduct(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean deleteSuccessful = db.delete("products", "id ='" + ID + "'", null) > 0;
        db.close();
        return deleteSuccessful;
    }

    public boolean updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("quantity", product.getQuantity());
        values.put("price", product.getPrice());
        values.put("status", product.getStatus());
        String where = "id = ?";

        String[] whereArgs = {Integer.toString(product.getID())};
        SQLiteDatabase db = this.getWritableDatabase();
        boolean updateSuccessful = db.update("products", values, where, whereArgs) > 0;
        db.close();
        return updateSuccessful;
    }

    public int countRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM products";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;

    }
}