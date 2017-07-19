package tum.hitchmeup;

/**
 * Created by serfk on 19.07.2017.
 */

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import tum.Models.BaseApplication;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    BaseApplication app;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        app = (BaseApplication) getApplication();
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        app.addToNewsList("Message received from Server: " + remoteMessage.getNotification().getBody());

    }

}