package io.revze.kamusku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "kamusku";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_INDO_ENGLISH_WORD = "create table " + DatabaseContract.INDO_ENGLISH_TBL +
            " (" + DatabaseContract.IndoEnglishColumns._ID + " integer primary key autoincrement, " +
            DatabaseContract.IndoEnglishColumns.WORD + " text not null, " +
            DatabaseContract.IndoEnglishColumns.DESCRIPTION + " text not null);";

    public static String CREATE_TABLE_ENGLISH_INDO_WORD = "create table " + DatabaseContract.ENGLISH_INDO_TBL +
            " (" + DatabaseContract.EnglishIndoColumns._ID + " integer primary key autoincrement, " +
            DatabaseContract.EnglishIndoColumns.WORD + " text not null, " +
            DatabaseContract.EnglishIndoColumns.DESCRIPTION + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO_ENGLISH_WORD);
        db.execSQL(CREATE_TABLE_ENGLISH_INDO_WORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.INDO_ENGLISH_TBL);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ENGLISH_INDO_TBL);
        onCreate(db);
    }
}
