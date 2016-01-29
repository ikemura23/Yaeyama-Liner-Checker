
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;

import com.ikmr.banbara23.yaeyama_liner_checker.ApplicationController;

/**
 * キャッシュ管理マネージャー
 */
public class CacheManager {
    private static CacheManager sCacheManager;
    private static Context mContext;

    public static CacheManager getInstance() {
        if (sCacheManager == null)
            sCacheManager = new CacheManager();
        mContext = ApplicationController.getInstance().getApplicationContext().getApplicationContext();
        return sCacheManager;
    }

    public boolean isNull(String key) {
        return TextUtils.isEmpty(PreferenceUtils.loadString(mContext, key));
    }

    public Parcelable get(String key) {

        return null;
    }

    public void put(String key, Object object) {

    }

    public boolean isExpiryDuration() {

        return true;
    }

}
