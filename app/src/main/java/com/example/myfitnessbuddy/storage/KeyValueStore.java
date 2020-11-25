package com.example.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class KeyValueStore {

    private static String KEY_VALUE_STORE_FILE_NAME = "contact_app_kv_store";
    private static final int DEFAULT_INT_VALUE = 0;

    // Ref needed to access SharedPreferences
    private Application app;

    public KeyValueStore(Application application) {
        this.app = application;
    }

    private SharedPreferences getPreferences()
    {
        return this.app.getSharedPreferences( KEY_VALUE_STORE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void writeValue(String key, int value )
    {
        this.getPreferences().edit().putInt(key, value).apply();
    }

    public int getIntValue( String key )
    {
        return this.getPreferences().getInt( key, DEFAULT_INT_VALUE );
    }

}
