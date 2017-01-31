package com.example.rndroid.sqlite_listview_project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rndroid on 2/1/17.
 */

public class MyDatabase {

    SQLiteDatabase sdb;
    MyHelper myHelper;

    MyDatabase(Context c){

//        myHelper = new MyHelper(c,"mydatabase.db", null,1);
        //released new version
        myHelper = new MyHelper(c,"mydatabase.db", null,2);
    }

    public void openDB(){
        sdb = myHelper.getWritableDatabase();
    }

    public void insertDetails(String name, String subject){
        ContentValues cv = new ContentValues();
        cv.put("sname", name);
        cv.put("ssubject", subject);
        sdb.insert("studentdetails", null, cv);
    }

    public Cursor queryreadStudent(){
        Cursor cursor = null;
        cursor = sdb.query("studentdetails", null, null, null, null, null, null);
        return cursor;
    }

    public void closeDB(){
        sdb.close();
    }


    public  class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table studentdetails (_id integer primary key, sname text, ssubject text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            switch (newVersion){
                case 2 : sqLiteDatabase.execSQL("create table jobs(_id integer primary key, jdesc text)");
//                    sqLiteDatabase.execSQL("drop table student if exists;");  // this will drop table if exists
                    sqLiteDatabase.execSQL("alter table studentdetails add column scomp");
            }
        }
    }
}
