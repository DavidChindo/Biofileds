package com.hics.biofields.Library;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Created by david.barrera on 8/31/17.
 */

public class Prefs {


    public static final String PREFS_NAME = Statics.WM_PREFS;
    private static Prefs singleton;
    private Context context;

    public Prefs(Context context) {
        this.context = context;
    }


    public static Prefs with(Context context) {
        if (singleton == null) {
            synchronized (Prefs.class) {
                if (singleton == null) {
                    if (context == null) {
                        throw new IllegalArgumentException("Context must not be null");
                    }
                    singleton = new Prefs(context);
                    singleton.context = context.getApplicationContext();
                }
            }
        }
        return singleton;
    }

    /**
     * @param key
     * @return
     */
    public int getInt(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public String getString(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public boolean getBoolean(String key){
        return context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE).getBoolean(key,false);
    }

    public long getLong(String key) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(key, -1L);
    }

    public Object getObject(String key, Class<?> fromClass) {
        try {
            String json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(key, null);
            if (json != null) {
                Gson gson = new Gson();
                return gson.fromJson(json, fromClass);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void putBoolean(String key,boolean value){
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key,value)
                .commit();
    }

    public void putInt(String key, int value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .commit();
    }

    public void putString(String key, String value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .commit();
    }

    public void putLong(String key, long value) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(key, value)
                .commit();
    }

    public void putObject(String key, Object value, Class<?> fromClass) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(value, fromClass);
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putString(key, json)
                    .commit();
        } catch (Exception e) {
            throw new IllegalArgumentException("Only plain objects are supported");
        }
    }
}

