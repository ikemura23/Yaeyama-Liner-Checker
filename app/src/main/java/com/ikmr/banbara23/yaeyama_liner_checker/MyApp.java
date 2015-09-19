
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by banbara23 on 15/09/19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
