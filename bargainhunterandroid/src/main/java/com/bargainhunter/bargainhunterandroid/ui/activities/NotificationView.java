package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;

public class NotificationView extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        Bundle data = getIntent().getExtras();

        TextView tv = (TextView) findViewById(R.id.tv_notification);
        tv.setText(data.getString("content"));

        TextView storeName = (TextView) findViewById(R.id.notif_storeNameView);
        storeName.setText(data.getString("storeName"));

        TextView storeAddr = (TextView) findViewById(R.id.notif_addressView);
        storeAddr.setText(data.getString("storeAddr"));
    }
}
