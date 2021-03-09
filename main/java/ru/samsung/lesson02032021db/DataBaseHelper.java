package ru.samsung.lesson02032021db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.*;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userbase.db";
    private static String DATABASE_PATH;
    static final String TABLE = "users";
    private static final int SCHEMA = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEAR = "year";
    private Context MyContext;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.MyContext = context;
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_YEAR + " INTEGER);");
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", " + COLUMN_YEAR
                + ") VALUES ('Jon Weck',1985);");
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", " + COLUMN_YEAR
                + ") VALUES ('Jon ',2000);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        //onCreate(db);
    }

    void create_db() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DATABASE_PATH);
            if (!file.exists()) {
                myInput = MyContext.getAssets().open(DATABASE_NAME);
                String outfileName = DATABASE_PATH;
                myOutput = new FileOutputStream(outfileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DataBaseHelper",e.getMessage());
        } finally {
            try {
                if (myOutput != null) myOutput.close();
                if (myInput != null) myInput.close();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("DataBaseHelper",e.getMessage());
            }

        }

    }


    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
