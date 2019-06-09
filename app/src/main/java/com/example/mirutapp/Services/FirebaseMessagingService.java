package com.example.mirutapp.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mirutapp.MainActivity;
import com.example.mirutapp.R;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.mirutapp.MiRutAppApplication.CHANNEL_1_ID;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingService";
    private NotificationManagerCompat notificationManager;

    public FirebaseMessagingService(){
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        sendNotification(title, message);
    }

    @Override
    public void onDeletedMessages() {
    }

    private void sendNotification(String title,String messageBody) {
        //intent with extra information in order to load news section
        Intent activityIntent = new Intent(this, MainActivity.class);
        activityIntent.putExtra("comesFromNotification", "postFragment");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_car)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}

