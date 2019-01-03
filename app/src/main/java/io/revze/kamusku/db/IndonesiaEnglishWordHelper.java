package io.revze.kamusku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import io.revze.kamusku.model.IndonesiaEnglishWord;

public class IndonesiaEnglishWordHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public IndonesiaEnglishWordHelper(Context context) {
        this.context = context;
    }

    public IndonesiaEnglishWordHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<IndonesiaEnglishWord> getAllData() {
        Cursor cursor = database.query(DatabaseContract.INDO_ENGLISH_TBL, null, null, null, null, null,
                DatabaseContract.IndoEnglishColumns._ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<IndonesiaEnglishWord> arrayList = new ArrayList<>();

        if (cursor.getCount() > 0) {
            do {
                IndonesiaEnglishWord indonesiaEnglishWord = new IndonesiaEnglishWord();
                indonesiaEnglishWord.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.IndoEnglishColumns._ID)));
                indonesiaEnglishWord.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.IndoEnglishColumns.WORD)));
                indonesiaEnglishWord.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.IndoEnglishColumns.DESCRIPTION)));

                arrayList.add(indonesiaEnglishWord);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insert(IndonesiaEnglishWord indonesiaEnglishWord) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.IndoEnglishColumns.WORD, indonesiaEnglishWord.getWord());
        initialValues.put(DatabaseContract.IndoEnglishColumns.DESCRIPTION, indonesiaEnglishWord.getDescription());
        return database.insert(DatabaseContract.INDO_ENGLISH_TBL, null, initialValues);
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
