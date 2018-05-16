package dbworker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import entity.Faculty;

/**
 * Created by misha on 18.2.18.
 */

public class DBHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 33;
    public static final String DATABASE_NAME = "db";
    public static final String TABLE_DICTIONARY_IT = "itTable";
    public static final String TABLE_SETTINGS = "Settings";
    public static final String KEY_ID = "_id";
    public static final String KEY_RUS = "RUS";
    public static final String KEY_ENG = "ENG";
    public static final String KEY_BEL = "BEL";
    public static final String KEY_KEY = "KEYY";
    public static final String KEY_VALUE = "VALUE";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_SETTINGS + "(" + KEY_ID
                + " integer primary key,"  + KEY_KEY
                + " text," + KEY_VALUE + " text)");

        for (Faculty fac: Faculty.values()) {


            db.execSQL("create table " + fac.name() + "(" + KEY_ID
                    + " integer primary key,"  + KEY_RUS
                    + " text," + KEY_ENG + " text," + KEY_BEL + " text" + ")");


        }

        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('versionIT', '1' )");
        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('versionFMMP', '1' )");
        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('versionFES', '1' )");
        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('versionATF', '1' )");
        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('versionFTK', '1' )");
        db.execSQL("insert into " + TABLE_SETTINGS + " (" + KEY_KEY + " , "  + KEY_VALUE + ") VALUES ('faculty', '0' )");



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Faculty fac: Faculty.values()) {
            db.execSQL("drop table if exists " + fac.name());
        }

        db.execSQL("drop table if exists " + TABLE_SETTINGS);
        onCreate(db);
    }
}