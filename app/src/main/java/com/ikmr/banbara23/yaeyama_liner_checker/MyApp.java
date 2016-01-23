
package com.ikmr.banbara23.yaeyama_liner_checker;

import timber.log.Timber;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by banbara23 on 15/09/19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Timber.plant(new Timber.DebugTree());
    }
}
