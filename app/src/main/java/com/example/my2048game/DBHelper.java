package com.example.my2048game;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GameCenterDB";
    //Declaramos variables de la primera tabla "users"
    private static final String TABLE = "Users";
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    //Declaramos variables de la segunda tablas "scores"
    public static final String T_SCORE = "Scores";
    public static final String KEY_TOTSCORE = "Total_Score";
    public static final String KEY_TIME = "time";
    public static final String KEY_GAME = "Game";

    private static final String TABLE_CREATE =
            "CREATE TABLE " +
                    TABLE + " (" +
                    KEY_USER + " TEXT PRIMARY KEY, " +
                    KEY_PASSWORD + " TEXT );";

    private static final String TABLE_SCORE_CREATE =
            "CREATE TABLE " +
                    T_SCORE + " (" +
                    KEY_USER + " TEXT, " +
                    KEY_TIME + " TEXT, " +
                    KEY_GAME + " TEXT, " +
                    KEY_TOTSCORE + " INTEGER );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_SCORE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public long insert(String user, String password){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);
        values.put(KEY_PASSWORD, password);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            mWritableDB.insert(TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        } return newId;
    }

    public boolean select(String user, String password){
        String [] args = new String[]{user,password};
        try{
            if(mReadableDB == null){
                mReadableDB = getReadableDatabase();
            }
            Cursor cursor = mReadableDB.rawQuery("SElECT user, password FROM Users WHERE user = ? " +
                    "and password = ?" , args);
            if (cursor !=null && cursor.getCount()>0){
                cursor.moveToFirst();
                if (user.equals(cursor.getString(0))){
                    return true;
                }
            }
        }catch (Exception e){
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }

        return false;
    }
    public int delete(String user) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(TABLE,
                    KEY_USER + " = ? ", new String[]{String.valueOf(user)});

        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }return deleted;
    }

    public int update(String user, String password){
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_USER, user);
            values.put(KEY_PASSWORD, password);
            mNumberOfRowsUpdated = mWritableDB.update(TABLE,
                    values, KEY_USER + " = ?",
                    new String[]{String.valueOf(user)});

        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }return mNumberOfRowsUpdated;
    }

    public Cursor search(String user) {
        String[] columns = new String[]{KEY_USER};
        String selection = KEY_USER + " LIKE ?";
        user = "%" + user + "%";
        String[] selectionArgs = new String[]{user};
        Cursor cursor = null;
        try{
            if(mReadableDB == null){
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.query(TABLE, columns, selection, selectionArgs, null, null, null);

        }catch (Exception e){
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }
        return cursor;
    }
}