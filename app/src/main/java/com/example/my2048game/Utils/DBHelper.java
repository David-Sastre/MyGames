package com.example.my2048game.Utils;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *
 */
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
    public static final String KEY_TOTSCORE = "total_Score";
    public static final String KEY_TIME = "time";
    public static final String KEY_GAME = "game";

    private static final String TABLE_CREATE =
            "CREATE TABLE " +
                    TABLE + " (" +
                    KEY_USER + " TEXT PRIMARY KEY, " +
                    KEY_PASSWORD + " TEXT );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecutamos las dos tablas y creamos un nuevo usuario admin.
        db.execSQL(TABLE_CREATE);
        db.execSQL("CREATE TABLE IF NOT EXISTS Scores ( user TEXT, game TEXT, time TEXT, total_Score TEXT, " +
                " FOREIGN KEY (user) REFERENCES " +
                "Users (user) ON DELETE CASCADE);");
        adminDB(db);
    }

    /**
     * Método para que las foreign keys se activen.(Predeterminado OFF)
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + T_SCORE);
        onCreate(db);
    }

    /**
     * @param db
     */
    private void adminDB(SQLiteDatabase db){
        String user = "admin";
        String password = "admin";
        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);
        values.put(KEY_PASSWORD, password);
        db.insert(TABLE, null, values);
    }

    /**
     * @param user
     * @param password
     * @return
     */
    public long insertPlayer(String user, String password){
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
            Log.d(TAG, "INSERT PLAYER EXCEPTION! " + e.getMessage());
        } return newId;
    }

    /**
     * @param user
     * @param game
     * @param time
     * @param total_score
     * @return
     */
    public long insertScore (String user, String game, String time, String total_score ){
        long newId = 0;
        String [] args = new String []{user, game, time, total_score};
        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);
        values.put(KEY_GAME, game);
        values.put(KEY_TIME, time);
        values.put(KEY_TOTSCORE, total_score);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            mWritableDB.insert(T_SCORE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT SCORES EXCEPTION! " + e.getMessage());
        }
        return newId;

    }

    /**
     * @param user
     * @param password
     * @return
     */
    public boolean selectUser(String user, String password){
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

    /**
     * @param user
     * @return
     */
    public int deleteUser(String user) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(TABLE,
                    KEY_USER + " = ? ", new String[]{(user)});

        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }return deleted;
    }

    /**
     * @param user
     * @param game
     * @param time
     * @param total_score
     * @return
     */
    public int deleteScore(String user, String game, String time, String total_score ) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(T_SCORE,
                    KEY_USER + "=? AND " + KEY_GAME + "=? AND " +
                            KEY_TIME + "=? AND " + KEY_TOTSCORE + "=?",
                    new String[] {user, game, time, total_score});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }return deleted;
    }

    /**
     * @param user
     * @param password
     * @return
     */
    public int updateUser(String user, String password){
        int updated = 0;
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(KEY_USER, user);
            values.put(KEY_PASSWORD, password);
            updated = mWritableDB.update(TABLE,
                    values, KEY_USER + " = ?",
                    new String[]{user});

        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }return updated;
    }

    /**
     * @param user
     * @return
     */
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

    /**
     * @param param
     * @return
     */
    public String [] getScores(String param) {
        //Creamos el cursor
        Cursor cursor = null;
        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery("SElECT * FROM Scores ORDER BY total_score ASC", null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }

        String[] list = new String[cursor.getCount()];
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto puntuación
                String listscore = cursor.getString(cursor.getColumnIndexOrThrow(param));
                list[i] = listscore;
                cursor.moveToNext();
            }
        }
        //Cerramos el cursor
        cursor.close();
        return list;
    }
}