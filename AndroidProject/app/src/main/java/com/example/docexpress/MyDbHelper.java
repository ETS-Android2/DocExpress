package com.example.docexpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    String create_user_table="CREATE TABLE users(user_id TEXT PRIMARY KEY,user_name TEXT,user_job TEXT)";
    String create_doc_ongoing="CREATE TABLE doc_ongoing(doc_id TEXT PRIMARY KEY,doc_name TEXT,start_date TEXT,end_date TEXT,app_id TEXT,app_name TEXT)";
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

    public Boolean insertUserID(String incoming_id,String user_name, String user_job)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", incoming_id);
        contentValues.put("user_name", user_name);
        contentValues.put("user_job", user_job);
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

    public void createDocOngoingTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase();
        MyDB.execSQL(create_doc_ongoing);
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

    public Boolean dropdocOngoingTable()
    {
        SQLiteDatabase MyDB=this.getWritableDatabase() ;
        MyDB.execSQL("DROP TABLE IF EXISTS doc_ongoing");
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

    public Boolean insertDocOngoingTable(String doc_id,String doc_name,String start_date,String end_date, String app_id,String app_name)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doc_id", doc_id);
        contentValues.put("doc_name", doc_name);
        contentValues.put("start_date", start_date);
        contentValues.put("end_date", end_date);
        contentValues.put("app_id", app_id);
        contentValues.put("app_name", app_name);
        long result = MyDB.insert("doc_ongoing",null,contentValues);
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

    public Cursor getdocOngoing()
    {
        SQLiteDatabase MyDB=this.getReadableDatabase() ;
        Cursor cursor=MyDB.rawQuery("SELECT * FROM doc_ongoing",null);
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
