
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsUtils {
    private static FirebaseAnalytics firebaseAnalytics;
    private static final String CONTENT_TYPE_ACTIVITY = "activity";
    private static final String CONTENT_TYPE_SELECT = "select";

    public static void initialize(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static void logAppOpenEvent(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_ACTIVITY);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
    }

    public static void logSelectEvent(String name, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, CONTENT_TYPE_SELECT);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
