package ru.samsung.lesson02032021db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        listView = findViewById(R.id.list);
        dataBaseHelper= new DataBaseHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        db=dataBaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from "+DataBaseHelper.TABLE,null);
        String[] headers = new String[]{DataBaseHelper.COLUMN_NAME,
                DataBaseHelper.COLUMN_YEAR};
        userAdapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                userCursor,
                headers,
                new int[]{android.R.id.text1,android.R.id.text2},0);
        textView.setText(""+userCursor.getCount());


        listView.setAdapter(userAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }

    /*public void onLoad(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db",
                MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
        db.execSQL("INSERT INTO users VALUES ('Jon Yuck',45);");
        db.execSQL("INSERT INTO users VALUES ('Jon ',50);");
        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        while (query.moveToNext()) {
            String name = query.getString(0);
            int age = query.getInt(1);
            textView.append("Name " + name + " Age " + age + "\n");
        }
        query.close();
        db.close();

    }*/
}