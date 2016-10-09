
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.nifty.cloud.mb.core.NCMB;
import com.socks.library.KLog;

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
        Base.initialize(this);
        NCMB.initialize(Base.getContext(),
                BuildConfig.NCMB_APPLICATION_ID,
                BuildConfig.NCMB_CLIENT_KEY);

        AnalyticsUtils.initialize(getApplicationContext());
        KLog.init(BuildConfig.DEBUG);
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
