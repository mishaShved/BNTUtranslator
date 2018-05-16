package window_creator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.misha.bntu_translator.R;
import com.example.misha.bntu_translator.TranslateActivity;

import dbworker.DBHelper;
import entity.Faculty;

/**
 * Created by misha on 18.2.18.
 */

public class Creator {

    public static void createTranslateWindow(TranslateActivity activity){


        SQLiteDatabase database = TranslateActivity.dbHelper.getWritableDatabase();

        int facInt = 0;

        String[] list = {"faculty"};
        String[] col = {DBHelper.KEY_VALUE};

        Cursor cursor = database.query(DBHelper.TABLE_SETTINGS, col, DBHelper.KEY_KEY + " = ?", list, null, null, null);
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(DBHelper.KEY_VALUE);
            do {
                facInt = Integer.parseInt(cursor.getString(index));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        TableRow faculty = (TableRow) activity.findViewById(R.id.faculties);

        for (Faculty fac: Faculty.values()) {
            Button button = new Button(activity);
            button.setText(fac.getNameFac());
            button.setOnClickListener(activity);
            button.setId(fac.ordinal());
            button.setTextSize(16f);

            if (fac.ordinal() == facInt){
                TranslateActivity.faculty = fac;
                button.setBackgroundResource(R.drawable.choisedfaculty);
            }else{
                button.setBackgroundResource(R.drawable.facultybuttons);
            }

            button.setMinWidth(activity.getWindowManager().getDefaultDisplay().getWidth()/4*3);


            TextView space = new TextView(activity);
            space.setText("   ");

            faculty.addView(button);
            faculty.addView(space);
        }
    }

    public static void createStartingWindow(Activity activity){

    }

}
