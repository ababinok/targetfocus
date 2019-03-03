package com.ofnicon.targetfocus.core;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.ofnicon.targetfocus.R;
import com.ofnicon.targetfocus.activities.NotificationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class Core {

    private static final String CHANNEL_ID = "target_focus";
    private static final String CHANNEL_NAME = "Уведомления приложения Целевой фокус";
    private static final String CHANNEL_DESC = "Основные уведомления";

    private static String getNotificationText(Context context) {
        List<String> notices;
        Set<String> savedNotices = MySharedPreferences.getStringSet(context, MySharedPreferences.GOALS_LIST_NAME);
        if (savedNotices != null) {
            notices = new ArrayList<>(savedNotices);
        } else {
            return context.getString(R.string.enter_your_goal);
        }
        return notices.get((new Random()).nextInt(notices.size()));
    }

    public static void startNotifications() {

        stopNotifications(); // Чтобы не было задублированных заданий
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .addTag(NotificationWorker.TAG)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class,
                60, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                .addTag(NotificationWorker.TAG)
                .build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);

    }

    public static void stopNotifications() {
        WorkManager.getInstance().cancelAllWorkByTag(NotificationWorker.TAG);
    }

    public static void shareNotice(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "Поделиться"));
    }

    static void displayNotification(Context context) {

        String text = Core.getNotificationText(context);
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.putExtra("text", text);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        Intent chooser = Intent.createChooser(sendIntent, "Поделиться");
        PendingIntent pendingSendIntent = PendingIntent.getActivity(context, 1, chooser, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.header_text))
                .setContentText(text)
                .setColor(context.getResources().getColor(R.color.primary_dark))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_share_24dp, context.getString(R.string.share), pendingSendIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }

    public static void initNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

}
