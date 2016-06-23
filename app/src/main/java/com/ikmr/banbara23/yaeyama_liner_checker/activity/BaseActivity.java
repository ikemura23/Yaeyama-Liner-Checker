
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ikmr.banbara23.yaeyama_liner_checker.Analytics;

public class BaseActivity extends AppCompatActivity {
    Analytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics = new Analytics(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analytics.logActivated(this);
    }
}
