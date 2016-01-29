
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Application;
import android.content.res.Resources;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
        Timber.plant(new Timber.DebugTree());

        if (mApplicationController == null) {
            mApplicationController = this;
        }
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }

    public static Resources getResorces() {
        return mApplicationController.getApplicationContext().getResources();
    }
}
