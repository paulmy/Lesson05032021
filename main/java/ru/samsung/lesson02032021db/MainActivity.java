package ru.samsung.lesson02032021db;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    EditText userFilter;
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
        userFilter = findViewById(R.id.userFilter);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        dataBaseHelper.create_db();

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
        try {
            db = dataBaseHelper.open();
            // db = dataBaseHelper.getReadableDatabase();
            userCursor = db.rawQuery("select * from " + DataBaseHelper.TABLE, null);
            String[] headers = new String[]{DataBaseHelper.COLUMN_NAME,
                    DataBaseHelper.COLUMN_YEAR};
            userAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.two_line_list_item,
                    userCursor,
                    headers,
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);
            textView.setText("" + userCursor.getCount());
            if (!userFilter.getText().toString().isEmpty())
                userAdapter.getFilter().filter(userFilter.getText().toString());
            userFilter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    userAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    if (constraint == null || constraint.length() == 0) {
                        return db.rawQuery("select * from " + DataBaseHelper.TABLE, null);
                    } else {
                        return db.rawQuery("select * from " + DataBaseHelper.TABLE + " where "
                                        + DataBaseHelper.COLUMN_NAME + " like ?",
                                new String[]{"%" + constraint.toString() + "%"});
                    }
                }
            });
            listView.setAdapter(userAdapter);
        }catch (SQLException e){}
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