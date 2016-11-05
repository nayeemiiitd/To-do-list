package com.iiitd.to_do_list;

/**
 * Created by Nayeem on 11/4/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo2.db";
    private static final String TABLE_NAME = "todo2_table";
    private static final String COL_1 = "TITLE";
    private static final String COL_2 = "DETAIL";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (TITLE TEXT PRIMARY KEY ,DETAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title,String detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,detail);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getdetail(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select DETAIL from "+TABLE_NAME+" where TITLE = ?",new String[]{ title });
        return res;
    }


    public Integer deleteData (String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "TITLE = ?",new String[] {title});
    }

    public Cursor readData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
