package com.example.misha.bntu_translator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import dbworker.DBHelper;
import entity.Dictionary;
import entity.Faculty;
import request.RequestController;
import updatedb.ThreadUpdaterDB;
import window_creator.Creator;

public class TranslateActivity extends AppCompatActivity implements View.OnClickListener{

    public static DBHelper dbHelper;
    public static Faculty faculty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        FloatingActionButton btnSearch = (FloatingActionButton)findViewById(R.id.search);
        Button btnChange = (Button)findViewById(R.id.changeLng);

        btnChange.setOnClickListener(this);
        btnChange.setBackgroundResource(R.drawable.change);
        btnSearch.setOnClickListener(this);
        btnSearch.setBackgroundResource(R.drawable.lupa);

        Spinner firstLanguage = (Spinner)findViewById(R.id.firstLanguage);
        firstLanguage.setSelection(1);



        EditText word = (EditText) findViewById(R.id.word);

        word.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    EditText editTextWord = (EditText) findViewById(R.id.word);
                    String word = editTextWord.getText().toString();
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    translate(word, database);
                    database.close();
                    scrollToTopick();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }

                return false;
            }
        });

        dbHelper = new DBHelper(this);

        Creator.createTranslateWindow(this);


        final HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.scroll);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
              scrollToTopick();
            }
        });

        ThreadUpdaterDB tudb = new ThreadUpdaterDB();

        tudb.run();




    }



    @Override
    public void onClick(View view) {


        EditText editTextWord = (EditText) findViewById(R.id.word);
        String word = editTextWord.getText().toString();
        switch (view.getId()){
            case R.id.changeLng:
                changeLanguge();
                break;
            case R.id.search:
                scrollToTopick();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                translate(word , database);
                database.close();
                scrollToTopick();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            default:
                Button button = (Button) findViewById(view.getId());
                changeTopick(Faculty.getFacultyByName(button.getText().toString()));
                SQLiteDatabase database1 = dbHelper.getWritableDatabase();
                translate(word , database1);
                database1.close();
                break;
        }
    }

    private void changeLanguge() {
        Spinner firstLanguage = (Spinner) findViewById(R.id.firstLanguage);
        Spinner secondLangeage = (Spinner) findViewById(R.id.secondLanguage);
        View view1 = firstLanguage.getSelectedView();

        int posicionFirstLanguage = firstLanguage.getPositionForView(view1);
        View view2 = secondLangeage.getSelectedView();
        int posicionSecondLanguage = secondLangeage.getPositionForView(view2);
        firstLanguage.setSelection(posicionSecondLanguage);
        secondLangeage.setSelection(posicionFirstLanguage);

    }


    private void translate(String word, SQLiteDatabase database ) {

        Spinner firstLanguage = (Spinner)findViewById(R.id.firstLanguage);
        Spinner secondLangeage = (Spinner) findViewById(R.id.secondLanguage);
        View view1 = firstLanguage.getSelectedView();
        int posicionFirstLanguage = firstLanguage.getPositionForView(view1);
        View view2 = secondLangeage.getSelectedView();
        int posicionSecondLanguage = secondLangeage.getPositionForView(view2);
        String languageFrom = null;
        String languageTo = null;
        switch (posicionFirstLanguage){
            case 0:
                languageFrom = DBHelper.KEY_RUS;
                break;
            case 1:
                languageFrom = DBHelper.KEY_ENG;
                break;
            case 2:
                languageFrom = DBHelper.KEY_BEL;
                break;
        }
        switch (posicionSecondLanguage ){
            case 0:
                languageTo = DBHelper.KEY_RUS;
                break;
            case 1:
                languageTo = DBHelper.KEY_ENG;
                break;
            case 2:
                languageTo = DBHelper.KEY_BEL;
                break;
        }

        String[] l = {languageTo, languageFrom};
        if (word == null || word.length() == 0) {
            word = "q";
        }
        word = word.toLowerCase();
        char[] q = word.toCharArray();
        String first = q[0] + "";
        first = first.toUpperCase();
        q[0] = first.charAt(0);
        word = new String(q);
        String[] l1 = {word};
        String[] l2 = {word+"%"};


        TableLayout table = (TableLayout) findViewById(R.id.tableRes);
        table.removeAllViews();

        Cursor cursor = database.query(faculty.name(), l, languageFrom + " like ?", l2, null, null, null);

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(languageTo);
            int index1 = cursor.getColumnIndex(languageFrom);
            do {
                String res = cursor.getString(index);
                String res1 = cursor.getString(index1);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(layoutParams);
                tableRow.setOrientation(LinearLayout.VERTICAL);

                TableRow tableRow1 = new TableRow(this);
                tableRow1.setLayoutParams(layoutParams);
                tableRow1.setOrientation(LinearLayout.VERTICAL);


                TextView translateWord1 = new TextView(this);
                translateWord1.setTextSize(2f);
                translateWord1.setText("\n");

                TextView translateWord = new TextView(this);
                translateWord.setText(res);
                translateWord.setTextSize(29);

                TextView wordTextView = new TextView(this);
                wordTextView.setText(res1);
                wordTextView.setTextSize(20);


                tableRow.addView(translateWord);
                tableRow1.addView(wordTextView);

                tableRow.setBackgroundColor(Color.parseColor("#F0FFFF"));
                tableRow1.setBackgroundColor(Color.parseColor("#F0FFFF"));


                TableLayout qqq = new TableLayout(this);
                qqq.addView(tableRow);
                qqq.addView(tableRow1);


                qqq.setColumnShrinkable(0, true);
                qqq.setBackgroundResource(R.drawable.resstyle);

                TableRow tb = new TableRow(this);
                TextView txt = new TextView(this);
                txt.setText(" ");
                tb.addView(txt);


                table.addView(qqq);
                table.addView(tb);

            } while (cursor.moveToNext());
        }
        cursor.close();


    }


    private void changeTopick(Faculty faculty){


        int i = 0;
        for (Faculty fac: Faculty.values()) {
            Button button = (Button) findViewById(i);
            button.setBackgroundResource(R.drawable.facultybuttons);
            i++;
        }

        Button button = (Button) findViewById(faculty.ordinal());
        button.setBackgroundResource(R.drawable.choisedfaculty);
        this.faculty = Faculty.getFacultyByName(button.getText().toString());
        int facInt = Faculty.getFacultyByName(button.getText().toString()).ordinal();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        switch (facInt){
            case 0:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '0' )");
                break;
            case 1:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '1' )");
                break;
            case 2:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '2' )");
                break;
            case 3:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '3' )");
                break;
            case 4:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '4' )");
                break;
            case 5:
                database.execSQL("insert into " + DBHelper.TABLE_SETTINGS + " (" + DBHelper.KEY_KEY + " , "  + DBHelper.KEY_VALUE + ") VALUES ('faculty', '5' )");
                break;
        }


        database.close();
    }

    private void scrollToTopick(){
        int x = this.getWindowManager().getDefaultDisplay().getWidth() * 3 / 4 + 5;

        HorizontalScrollView scroll = (HorizontalScrollView)findViewById(R.id.scroll);

        int scrollX = 0;

        for (Faculty fac : Faculty.values()) {
            if(fac == faculty){
                break;
            }
            scrollX += x;
        }

        scroll.scrollTo(scrollX, 0);
    }
}

