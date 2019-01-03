package io.revze.kamusku;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    private SharedPreferences prefs;
    private Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setIdEngSyncStatus(boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.id_eng_sync_status);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public void setEngIdSyncStatus(boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.eng_id_sync_status);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public boolean getIdEngSyncStatus() {
        String key = context.getResources().getString(R.string.id_eng_sync_status);
        return prefs.getBoolean(key, false);
    }

    public boolean getEngIdSyncStatus() {
        String key = context.getResources().getString(R.string.eng_id_sync_status);
        return prefs.getBoolean(key, false);
    }
}
