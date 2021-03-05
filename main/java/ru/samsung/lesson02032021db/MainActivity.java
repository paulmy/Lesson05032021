package ru.samsung.lesson02032021db;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
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
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        db = dataBaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from " + DataBaseHelper.TABLE, null);
        String[] headers = new String[]{DataBaseHelper.COLUMN_NAME,
                DataBaseHelper.COLUMN_YEAR};
        userAdapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                userCursor,
                headers,
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        textView.setText("" + userCursor.getCount());
        listView.setAdapter(userAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }

    public void add(View view) {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);

    }
}