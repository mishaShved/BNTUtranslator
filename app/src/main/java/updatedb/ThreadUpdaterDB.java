package updatedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.misha.bntu_translator.TranslateActivity;
import com.google.gson.Gson;

import dbworker.DBHelper;
import entity.Dictionary;
import entity.Faculty;
import request.RequestController;

public class ThreadUpdaterDB implements Runnable{
    @Override
    public void run() {

        Gson gson = new Gson();
        int oldVersion = 0;


        for (Faculty fac: Faculty.values()) {
            SQLiteDatabase database = TranslateActivity.dbHelper.getWritableDatabase();

            String[] list = {"version" + fac.name()};
            String[] col = {DBHelper.KEY_VALUE};

            Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, col, DBHelper.KEY_KEY + " = ?", list, null, null, null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DBHelper.KEY_VALUE);
                do {
                    oldVersion = Integer.parseInt(cursor.getString(index));
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
            if (RequestController.getVersion(fac.name()) != -1) {
                if (oldVersion != RequestController.getVersion(fac.name())) {
                    Dictionary dictionary = gson.fromJson(RequestController.getDB(fac.name()), Dictionary.class);
                    UpdaterDB updater = new UpdaterDB();
                    updater.update(dictionary, RequestController.getVersion(fac.name()), TranslateActivity.dbHelper, fac.name());
                }
            }
            else {
                break;
            }
        }

    }
}
