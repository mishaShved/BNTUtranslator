package updatedb;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import dbworker.DBHelper;
import entity.Dictionary;
import entity.Word;

/**
 * Created by misha on 18.2.18.
 */

public class UpdaterDB {
    public void update(Dictionary dictionary, int version, DBHelper dbHelper, String type){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] r = {type};
        database.delete(DBHelper.TABLE_SETTINGS, DBHelper.KEY_VALUE + " = ?", r);

        database.execSQL("delete from " + type + ";");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_KEY, "version" + type);
        contentValues.put(DBHelper.KEY_VALUE, version);
        database.insert(DBHelper.TABLE_SETTINGS, null, contentValues);
        contentValues.clear();

        for (Word el : dictionary.getDictionary()) {
            contentValues.put(DBHelper.KEY_BEL, el.getBel());
            contentValues.put(DBHelper.KEY_ENG, el.getEng());
            contentValues.put(DBHelper.KEY_RUS, el.getRus());
            database.insert(type, null, contentValues);
            contentValues.clear();
        }
        database.close();
    }
}
