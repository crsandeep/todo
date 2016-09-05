package com.codepath.doit.utils;

import com.activeandroid.query.Select;
import com.codepath.doit.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sraveesh on 9/5/16.
 */
public class DBUtils {

    public static void writeOne(Item item) {
        item.save();
    }

    public static void writeMany(ArrayList<Item> items) {

    }

    public static List<Item> readAll() {
        return new Select()
                .from(Item.class)
                .execute();
    }

}
