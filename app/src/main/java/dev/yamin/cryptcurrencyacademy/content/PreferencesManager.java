package dev.yamin.cryptcurrencyacademy.content;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import lib.yamin.easylog.EasyLog;

/**
 * Created by Yamin on 12-May-18.
 */
@Singleton
public class PreferencesManager {

    private SharedPreferences settings;

    @Inject
    public PreferencesManager(SharedPreferences settings) {
        this.settings = settings;
    }

    public void add(String key, String value) {
        settings.edit().putString(key, value).apply();
    }

    public void add(String key, int value) {
        settings.edit().putInt(key, value).apply();
    }

    public void add(String key, long value) {
        settings.edit().putLong(key, value).apply();
    }

    public void add(String key, boolean value) {
        settings.edit().putBoolean(key, value).apply();
    }

    public String getString(String key) {
        return settings.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return settings.getBoolean(key, false);
    }

    public int getInt(String key) {
        return settings.getInt(key, -1);
    }

    public long getLong(String key) {
        return settings.getLong(key, -1);
    }

    public void remove(String key) {
        settings.edit().remove(key).apply();
        EasyLog.e(key);
    }

    public void clearAll() {
        settings.edit().clear().apply();
    }

    @Override
    public String toString() {
        return "PreferencesManager{" +
                "settings=" + (settings != null) +
                '}';
    }
}
