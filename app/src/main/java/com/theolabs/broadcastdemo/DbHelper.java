package com.theolabs.broadcastdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Prejin on 11/4/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="numberDb";
    private static final int DATABASE_VERSION=1;
    private static final String CREATE="create table "+DbContract.TABLENAME+"(id integer primary key autoIncrement,"+
            DbContract.INCOMING_NUMBER+" text);";
    private static final String DROP_TABLE="drop table if exists "+DbContract.TABLENAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }
    public void saveNumber(String number,SQLiteDatabase database){
        ContentValues contentValues =new ContentValues();
        contentValues.put(DbContract.INCOMING_NUMBER,number);
        database.insert(DbContract.TABLENAME,null,contentValues);

    }

    public Cursor readNumber(SQLiteDatabase database){

        String [] projection ={"id",DbContract.INCOMING_NUMBER};
        return (database.query(DbContract.TABLENAME,projection,null,null,null,null,null));
    }
}
