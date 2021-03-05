package ru.samsung.lesson02032021db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userbase.db";
    static final String TABLE = "users";
    private static final int SCHEMA = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEAR = "year";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_YEAR + " INTEGER);");
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", " + COLUMN_YEAR
                + ") VALUES ('Jon Weck',1985);");
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", " + COLUMN_YEAR
                + ") VALUES ('Jon ',2000);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
