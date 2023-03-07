package com.example.chatroom.fcm;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.chatroom.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(message.getData().size() > 0){
            Map<String,String> data = message.getData();
            handleData(data);
        }else if(message.getNotification()!= null){
            handleNotification(message.getNotification());
        }
    }

    private void handleNotification(RemoteMessage.Notification notification) {
        String message = notification.getBody();
        String title = notification.getTitle();
        NotificationVO notificationVO = new NotificationVO();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.displayNotification(notificationVO,resultIntent);
        notificationUtils.playNotificationSound();
    }

    private void handleData(Map<String, String> data) {
        String title = data.get("title");
        String message = data.get("message");
        NotificationVO notificationVO = new NotificationVO();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);

        Intent resultIntent = new Intent(getApplicationContext(),MainActivity.class);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.displayNotification(notificationVO,resultIntent);
        notificationUtils.playNotificationSound();
    }

    private void sendRegistrationToServer(String token) {
    }
}
