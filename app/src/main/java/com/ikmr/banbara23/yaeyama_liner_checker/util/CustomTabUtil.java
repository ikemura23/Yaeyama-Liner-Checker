
package com.ikmr.banbara23.yaeyama_liner_checker.util;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;

public class CustomTabUtil {

    public static void start(Activity activity, String urlString) {
        if (StringUtils.isEmpty(urlString)) {
            urlString = Const.weatherUrl;
        }

        Uri uri = Uri.parse(urlString);

        final CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.primary))
                .build();

        // Chromeの起動
        tabsIntent.launchUrl(activity, uri);
    }
}
