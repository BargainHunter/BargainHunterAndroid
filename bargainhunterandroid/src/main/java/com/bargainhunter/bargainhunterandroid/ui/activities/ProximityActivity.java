package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.R;


public class ProximityActivity extends Activity {

    String notificationTitle;
    String notificationContent;
    String tickerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        boolean proximity_entering = getIntent().getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);

        double lat = getIntent().getDoubleExtra("lat", 0);

        double lng = getIntent().getDoubleExtra("lng", 0);

        String theStoreName = getIntent().getStringExtra("storename");
        String theStoreAddr = getIntent().getStringExtra("storeaddr");
        int reqCode = getIntent().getIntExtra("reqcode", 0);

        String strLocation = Double.toString(lat) + "," + Double.toString(lng);

        String StoreOffers = getIntent().getStringExtra("StoreOffers");
        if (proximity_entering) {
            //Toast.makeText(getBaseContext(),"Entering the region"  ,Toast.LENGTH_LONG).show();
            notificationTitle = "You are Near " + theStoreName + " " + theStoreAddr;
            notificationContent = "New Offers: " + "\n" + theStoreName + " " + StoreOffers;
            tickerMessage = "New Offers: " + theStoreName + " " + theStoreAddr;
        } else {
            Toast.makeText(getBaseContext(), "Exiting the region", Toast.LENGTH_LONG).show();
            notificationTitle = "Proximity - Exit";
            notificationContent = "Exited the region:" + strLocation;
            tickerMessage = "Exited the region:" + strLocation;
        }

        Intent notificationIntent = new Intent(getApplicationContext(), NotificationView.class);

        /** Adding content to the notificationIntent, which will be displayed on
         * viewing the notification
         */
        notificationIntent.putExtra("content", notificationContent);

        /** This is needed to make this intent different from its previous intents */
        notificationIntent.setData(Uri.parse("tel:/" + (int) System.currentTimeMillis()));

        /** Creating different tasks for each notification. See the flag Intent.FLAG_ACTIVITY_NEW_TASK */

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), reqCode, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        /** Getting the System service NotificationManager */
        NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        /** Configuring notification builder to create a notification */
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setWhen(System.currentTimeMillis())
                .setContentText(notificationContent)
                .setContentTitle(notificationTitle)
                .setSmallIcon(R.drawable.shop_icon)
                .setAutoCancel(true)
                .setTicker(tickerMessage)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        /** Creating a notification from the notification builder */
        Notification notification = notificationBuilder.build();

        /** Sending the notification to system.
         * The first argument ensures that each notification is having a unique id
         * If two notifications share same notification id, then the last notification replaces the first notification
         * */
        nManager.notify((int) System.currentTimeMillis(), notification);

        /** Finishes the execution of this activity */
        finish();
    }
}