
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    private static Analytics analytics;
    private static FirebaseAnalytics firebaseAnalytics;
    private static final String CONTENT_TYPE_ACTIVITY = "activity";
    private static final String CONTENT_TYPE_SELECT = "select";

    public Analytics(Context context) {
        if (context == null) {
            return;
        }
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static Analytics getInstance(Context context) {
        if (analytics == null) {
            analytics = new Analytics(context);
        }
        return analytics;
    }

    public static void logActivated(Activity activity) {
        if (activity == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_ACTIVITY);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, activity.getClass().getSimpleName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
    }

    public static void logEvent(String item) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, item);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_SELECT);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
