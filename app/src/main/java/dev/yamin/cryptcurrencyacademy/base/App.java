package dev.yamin.cryptcurrencyacademy.base;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dev.yamin.cryptcurrencyacademy.BuildConfig;
import dev.yamin.cryptcurrencyacademy.alerts.Room.AppDatabase;
import dev.yamin.cryptcurrencyacademy.di.DaggerAppComponent;
import lib.yamin.easylog.EasyLog;
import lib.yamin.easylog.EasyLogFormatter;

/**
 * Created by Yamin on 3/10/2018.
 */

public class App extends Application implements HasActivityInjector {

    private static Context context;
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EasyLog.showLogs(BuildConfig.DEBUG);
        EasyLog.setFormatter(new EasyLogFormatter() {
            @Override
            public String format(String classname, String methodName, int lineNumber) {
                return String.format(Locale.getDefault(), "[%d] %s.%s() => ", lineNumber, classname, methodName);
            }
        });
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "CoinAlertDB").build();

        DaggerAppComponent.builder().create(this).inject(this);
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
