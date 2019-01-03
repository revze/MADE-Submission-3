package io.revze.kamusku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import io.revze.kamusku.model.EnglishIndonesiaWord;

public class EnglishIndonesiaWordHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public EnglishIndonesiaWordHelper(Context context) {
        this.context = context;
    }

    public EnglishIndonesiaWordHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<EnglishIndonesiaWord> getAllData() {
        Cursor cursor = database.query(DatabaseContract.ENGLISH_INDO_TBL, null, null, null, null, null,
                DatabaseContract.EnglishIndoColumns._ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<EnglishIndonesiaWord> arrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            do {
                EnglishIndonesiaWord englishIndonesiaWord = new EnglishIndonesiaWord();
                englishIndonesiaWord.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EnglishIndoColumns._ID)));
                englishIndonesiaWord.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EnglishIndoColumns.WORD)));
                englishIndonesiaWord.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EnglishIndoColumns.DESCRIPTION)));

                arrayList.add(englishIndonesiaWord);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insert(EnglishIndonesiaWord englishIndonesiaWord) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.EnglishIndoColumns.WORD, englishIndonesiaWord.getWord());
        initialValues.put(DatabaseContract.EnglishIndoColumns.DESCRIPTION, englishIndonesiaWord.getDescription());
        return database.insert(DatabaseContract.ENGLISH_INDO_TBL, null, initialValues);
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }
}
