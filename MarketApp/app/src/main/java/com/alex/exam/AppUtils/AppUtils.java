package com.alex.exam.AppUtils;

import com.alex.exam.Activity.ActivityMain;
import com.alex.exam.Database.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppUtils {

    public static List<Product> sortProductsByQuantity() {
        List<Product> thisProducts = ActivityMain.products;
        Collections.sort(thisProducts, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getQuantity().compareTo(p2.getQuantity());
            }
        });
        return thisProducts;
    }

    public static List<Product> getAvailable() {
        List<Product> thisProducts = ActivityMain.products;
        Integer pos = 0;
        while (pos < thisProducts.size())
            if (thisProducts.get(pos).getStatus().equals("sold"))
                thisProducts.remove(thisProducts.get(pos));
            else pos++;
        return thisProducts;
    }
}
