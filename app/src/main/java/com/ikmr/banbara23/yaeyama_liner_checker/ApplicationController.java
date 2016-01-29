
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Application;

public class ApplicationController extends Application {

    private static ApplicationController mApplicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationController = this;
    }

    public static synchronized ApplicationController getInstance() {
        return mApplicationController;
    }
}
