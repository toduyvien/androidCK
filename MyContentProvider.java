package com.example.bai1;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {

    static final String AUTHOITY="com.example.bai1";
    static final String CONTENT_PROVIDER="MyContentProvider";
    static final String url="content://"+AUTHOITY+"/"+CONTENT_PROVIDER;
    public static final String PROVIDER_NAME = "com.example.bai1";




    static final  Uri CONTENT_URI=Uri.parse(url);
    static final String PRODUCT_TABLE="user";

    SQLiteDatabase db;
    static final int ONE=1;
    static final int ALL=2;

    static UriMatcher uriMatcher ;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOITY,PRODUCT_TABLE,ONE);
        uriMatcher.addURI(AUTHOITY,PRODUCT_TABLE+"/#",ALL);

    }
    private static HashMap<String,String> PROJECT_MAP;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        count=db.delete("user",selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        String red =getContext().getContentResolver().getType(CONTENT_URI);
        return red;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long number_row=db.insert(PRODUCT_TABLE,"",values);
        if (number_row>0){
            Uri uri1= ContentUris.withAppendedId(CONTENT_URI,number_row);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Context context=getContext();
        Sqlite sqlite=new Sqlite(context);
        db=sqlite.getWritableDatabase();
        if (db==null)
            return false;

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder sqLiteQueryBuilder=new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(PRODUCT_TABLE);
        switch (uriMatcher.match(uri)){
            case ALL:
                sqLiteQueryBuilder.setProjectionMap(PROJECT_MAP);
                break;
            case ONE:
                sqLiteQueryBuilder.appendWhere("sdt"+"="+uri.getPathSegments().get(0));
                break;
        }
        if (sortOrder==null||sortOrder==""){
            sortOrder="name";
        }
        Cursor cursor=sqLiteQueryBuilder.query(db,projection,selection,selectionArgs,null,
                null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        count=db.update("user",values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

}