package dev.yamin.cryptcurrencyacademy.base;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Locale;

import dev.yamin.cryptcurrencyacademy.BuildConfig;
import dev.yamin.cryptcurrencyacademy.alerts.Room.AppDatabase;
import lib.yamin.easylog.EasyLog;
import lib.yamin.easylog.EasyLogFormatter;

/**
 * Created by Yamin on 3/10/2018.
 */

public class App extends Application {

    private static Context context;

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
                return String.format(Locale.getDefault(), "%s.%s()[%d] => ", classname, methodName, lineNumber);
            }
        });
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "CoinAlertDB").build();
    }

    public static Context getContext() {
        return context;
    }
}
