package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;

public class NotificationView extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        TextView tv = (TextView) findViewById(R.id.tv_notification);
        Bundle data = getIntent().getExtras();
        tv.setText(data.getString("content"));
    }
}
