package io.revze.kamusku.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.revze.kamusku.AppPreference;
import io.revze.kamusku.R;
import io.revze.kamusku.db.EnglishIndonesiaWordHelper;
import io.revze.kamusku.db.IndonesiaEnglishWordHelper;
import io.revze.kamusku.model.EnglishIndonesiaWord;
import io.revze.kamusku.model.IndonesiaEnglishWord;


public class SplashscreenActivity extends AppCompatActivity {

    private TextView tvSyncStatus;
    private ProgressBar progressBar;
    private Context context;
    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        context = this;
        appPreference = new AppPreference(context);
        boolean isEngIdSynchronized = appPreference.getEngIdSyncStatus();
        boolean isIdEngSynchronized = appPreference.getIdEngSyncStatus();

        tvSyncStatus = findViewById(R.id.tv_sync_status);
        progressBar = findViewById(R.id.progress_bar);

        if (isEngIdSynchronized && isIdEngSynchronized) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            new LoadDataIndonesiaEnglish().execute();
        }
    }

    private ArrayList<IndonesiaEnglishWord> preLoadRawIndoEnglish() {
        ArrayList<IndonesiaEnglishWord> indonesiaEnglishWords = new ArrayList<>();
        String line = null;

        try {
            Resources res = getResources();
            InputStream rawWord = res.openRawResource(R.raw.indonesia_english);

            BufferedReader reader = new BufferedReader(new InputStreamReader(rawWord));

            do {
                line = reader.readLine();
                String[] splitStr = line.split("\t");
                IndonesiaEnglishWord indonesiaEnglishWord = new IndonesiaEnglishWord(splitStr[0], splitStr[1]);
                indonesiaEnglishWords.add(indonesiaEnglishWord);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return indonesiaEnglishWords;
    }

    private ArrayList<EnglishIndonesiaWord> preLoadRawEnglishIndo() {
        ArrayList<EnglishIndonesiaWord> englishIndonesiaWords = new ArrayList<>();
        String line = null;

        try {
            Resources res = getResources();
            InputStream rawWord = res.openRawResource(R.raw.english_indonesia);

            BufferedReader reader = new BufferedReader(new InputStreamReader(rawWord));

            do {
                line = reader.readLine();
                String[] splitStr = line.split("\t");
                EnglishIndonesiaWord englishIndonesiaWord = new EnglishIndonesiaWord(splitStr[0], splitStr[1]);
                englishIndonesiaWords.add(englishIndonesiaWord);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return englishIndonesiaWords;
    }

    private class LoadDataIndonesiaEnglish extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadDataIndonesiaEnglish.class.getSimpleName();
        IndonesiaEnglishWordHelper helper;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            tvSyncStatus.setText(getString(R.string.indonesia_sync_status));
            helper = new IndonesiaEnglishWordHelper(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean isIdEngSynchronized = appPreference.getIdEngSyncStatus();

            if (!isIdEngSynchronized) {
                ArrayList<IndonesiaEnglishWord> indonesiaEnglishWords = preLoadRawIndoEnglish();

                helper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / indonesiaEnglishWords.size();

                helper.beginTransaction();
                try {
                    for (IndonesiaEnglishWord model : indonesiaEnglishWords) {
                        helper.insert(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    helper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground Exception");
                }
                helper.endTransaction();

                helper.close();

                appPreference.setIdEngSyncStatus(true);

                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {

                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new LoadDataEnglishIndonesia().execute();
        }
    }

    private class LoadDataEnglishIndonesia extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadDataEnglishIndonesia.class.getSimpleName();
        EnglishIndonesiaWordHelper helper;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            tvSyncStatus.setText(getString(R.string.english_sync_status));
            helper = new EnglishIndonesiaWordHelper(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean isEngIdSynchronized = appPreference.getEngIdSyncStatus();

            if (!isEngIdSynchronized) {
                ArrayList<EnglishIndonesiaWord> englishIndonesiaWords = preLoadRawEnglishIndo();

                helper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / englishIndonesiaWords.size();

                helper.beginTransaction();
                try {
                    for (EnglishIndonesiaWord model : englishIndonesiaWords) {
                        helper.insert(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    helper.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground Exception");
                }
                helper.endTransaction();

                helper.close();

                appPreference.setEngIdSyncStatus(true);

                publishProgress((int) maxProgress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {

                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}
