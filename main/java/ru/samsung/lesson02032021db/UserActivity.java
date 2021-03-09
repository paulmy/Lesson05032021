package ru.samsung.lesson02032021db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UserActivity extends Activity {
    EditText name, year;
    Button btn_save, btn_delete;

    DataBaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name = findViewById(R.id.name);
        year = findViewById(R.id.year);
        btn_delete = findViewById(R.id.btn_delete);
        btn_save = findViewById(R.id.btn_save);
        sqlHelper = new DataBaseHelper(this);
        //db = sqlHelper.getWritableDatabase();
        db = sqlHelper.open();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            userId = extras.getLong("id");
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + DataBaseHelper.TABLE +
                            " where " + DataBaseHelper.COLUMN_ID + " =?",
                    new String[]{String.valueOf(userId)});

            userCursor.moveToFirst();
            name.setText(userCursor.getString(1));
            year.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();

        }
        if (userId == 0) {
            btn_delete.setVisibility(View.GONE);
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(DataBaseHelper.COLUMN_NAME, name.getText().toString());
                cv.put(DataBaseHelper.COLUMN_YEAR, Integer.parseInt(year.getText().toString()));
                if (userId > 0)
                    db.update(DataBaseHelper.TABLE, cv,
                            DataBaseHelper.COLUMN_ID + "=" + String.valueOf(userId), null);
                else
                    db.insert(DataBaseHelper.TABLE, null, cv);

                goHome();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(DataBaseHelper.TABLE,
                        "_id = ?",
                        new String[]{String.valueOf(userId)});
                goHome();
            }
        });

    }

    private void goHome() {
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
