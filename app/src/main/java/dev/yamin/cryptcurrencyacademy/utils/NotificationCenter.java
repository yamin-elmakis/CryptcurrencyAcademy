package dev.yamin.cryptcurrencyacademy.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import dev.yamin.cryptcurrencyacademy.R;
import dev.yamin.cryptcurrencyacademy.base.App;
import dev.yamin.cryptcurrencyacademy.main.CryptocurrencyActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Yamin on 3/8/2018.
 */

public class NotificationCenter {

    private static final NotificationCenter ourInstance = new NotificationCenter();

    public static NotificationCenter getInstance() {
        return ourInstance;
    }

    private NotificationCenter() {
    }

    public void createNotification(String text, String title) {
        Context context = App.getContext();
        Intent intent = new Intent(context, CryptocurrencyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        if (notificationManager != null)
            notificationManager.notify(0, notification);
    }


}


