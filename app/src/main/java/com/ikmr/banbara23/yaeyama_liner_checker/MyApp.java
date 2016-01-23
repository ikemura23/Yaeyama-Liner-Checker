
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by banbara23 on 15/09/19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
        Timber.plant(new Timber.DebugTree());
    }
}
