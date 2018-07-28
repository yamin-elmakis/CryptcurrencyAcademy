package dev.yamin.cryptcurrencyacademy.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yamin on 12-May-18.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
    }
}
