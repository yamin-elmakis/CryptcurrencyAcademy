package dev.yamin.cryptcurrencyacademy.alerts.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by yuval on 16/03/2018.
 */
@Database(entities = {CoinAlertItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singleton;
    private static final String DATABASE_NAME = "CoinAlertDb.db";
    public abstract CoinAlertDao coinAlertDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (singleton == null) {
            synchronized (AppDatabase.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries()//option
                            .build();
                }
            }

        }
        return singleton;
    }

}