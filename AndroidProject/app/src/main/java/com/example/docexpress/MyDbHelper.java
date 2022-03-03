package com.example.docexpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    String create_user_table="CREATE TABLE users(user_id TEXT PRIMARY KEY)";
    String create_doc_summary_table="CREATE TABLE doc_summary(table_id TEXT PRIMARY KEY,total TEXT,completed TEXT, ongoing TEXT,returned TEXT,rejected TEXT)";
    String delete_user_table="DROP TABLE IF EXISTS users";
    String create_doc_track_table="CREATE TABLE doctrack(step TEXT PRIMARY KEY, rec_date TEXT, emp_id TEXT,comments TEXT)";
    public MyDbHelper(@Nullable Context context) {
        super(context, "docexpress.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_user_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(delete_user_table);
        //onCreate(db);
    }
    public Boolean insertUserID(String incoming_id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", incoming_id);
        long result = MyDB.insert("users",null,contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }
    public void createDocTrackTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
         MyDB.execSQL(create_doc_track_table);
         MyDB.close();
    }
    public void createDocSummaryTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        MyDB.execSQL(create_doc_summary_table);
        MyDB.close();
    }
    public Boolean dropdoctrackTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase() ;
        MyDB.execSQL("DROP TABLE IF EXISTS doctrack");
        //MyDB.close();
        return true;
    }
    public Boolean dropdocSummaryTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase() ;
        MyDB.execSQL("DROP TABLE IF EXISTS doc_summary");
        //MyDB.close();
        return true;
    }
    public Boolean insertDocTrackDetails(String step,String rec_date,String emp_id,String comments)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("step", step);
        contentValues.put("rec_date", rec_date);
        contentValues.put("emp_id", emp_id);
        contentValues.put("comments", comments);
        long result = MyDB.insert("doctrack",null,contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean insertDocSummaryTable(String total,String completed,String ongoing,String returned, String rejected)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("table_id", "1");
        contentValues.put("total", total);
        contentValues.put("completed", completed);
        contentValues.put("ongoing", ongoing);
        contentValues.put("returned", returned);
        contentValues.put("rejected", rejected);
        long result = MyDB.insert("doc_summary",null,contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }
    public Cursor getdoctracking()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase() ;
        Cursor cursor=MyDB.rawQuery("SELECT * FROM doctrack",null);
        //MyDB.close();
        return cursor;
    }
    public Cursor getdocSummary()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase() ;
        Cursor cursor=MyDB.rawQuery("SELECT * FROM doc_summary",null);
        //MyDB.close();
        return cursor;
    }
    public Boolean dropUserIdTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase() ;
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        //MyDB.close();
        return true;
    }
    public Boolean deleteUserId()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase() ;
        MyDB.execSQL("DELETE FROM users");
        //MyDB.close();
        return true;
    }
    public Cursor getuserCount()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase() ;
        Cursor cursor=MyDB.rawQuery("SELECT * FROM users",null);
        //MyDB.close();
        return cursor;
    }
    public Cursor getuserID()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase() ;
        Cursor cursor=MyDB.rawQuery("SELECT user_id FROM users",null);
        //MyDB.close();
        return cursor;
    }
}
