package com.example.bai1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Sqlite extends SQLiteOpenHelper {
    public Sqlite(Context context) {
        super((Context) context, "zaloFake3",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Script.
        String script = "CREATE TABLE " + "user" + "("
                + "sdt" + " TEXT PRIMARY KEY," + "ten" + " TEXT,"
                + "matkhau" + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DRop table if exists user");
        onCreate(db);
    }
    public void addUser(User u) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sdt", u.getSdt());
        values.put("ten", u.getTen());
        values.put("matkhau", u.getMatkhau());

        // Inserting Row
           db.insert("user", null, values);

        // Closing database connection
        db.close();
    }
    public User getUser(String sdt) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("user", new String[] { "sdt",
                        "ten", "matkhau" }, "sdt" + "=?",
                new String[] { String.valueOf(sdt) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User((cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return user;
    }
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "user";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setSdt(cursor.getString(0));
                user.setTen(cursor.getString(1));
                user.setMatkhau(cursor.getString(2));
                // Adding note to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return note list
        return userList;
    }
    public int getNotesCount() {
        Log.i("SQLite", "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + "user";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ten", user.getTen());
        values.put("matkhau",user.getMatkhau());

        // updating row
        return db.update("user", values, "sdt" + " = ?",
                new String[]{String.valueOf(user.getSdt())});
    }
    public void deleteuser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", "sdt" + " = ?",
                new String[] {String.valueOf(user.getSdt())});

    }
}

