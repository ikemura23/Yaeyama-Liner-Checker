
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.os.Parcelable;
import android.text.TextUtils;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;

import java.util.Date;

/**
 * キャッシュ管理マネージャー
 */
public class CacheManager {
    private static CacheManager sCacheManager;

    public static CacheManager getInstance() {
        if (sCacheManager == null)
            sCacheManager = new CacheManager();
        return sCacheManager;
    }

    public boolean isNull(String key) {
        return TextUtils.isEmpty(PreferenceUtils.loadString(key));
    }

    public Parcelable get(String key) {
        return JsonUtil.fromJson(PreferenceUtils.loadString(key));
    }

    public void putResult(String key, Result result) {
        PreferenceUtils.saveString(key, JsonUtil.toJsonFromResult(result));
    }

    public boolean isExpiry() {
        long duration = getDuration();
        return true;
    }

    private long loadAnneiListTimeStamp() {
        return PreferenceUtils.loadLong(Const.TIMESTAMP_ANNEI_LIST_KEY);
    }

    private long getDuration() {
        // DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
        // DateFormat.MEDIUM, Locale.getDefault());
        Date dateNow = new Date();

        // 日付をlong値に変換します。
        long timeStamp = loadAnneiListTimeStamp();
        long nowTimeStamp = dateNow.getTime();

        // 差分の分を算出
        return (timeStamp - nowTimeStamp) / (1000 * 60);
    }

    public void saveTimeStamp(String key, long timestamp) {
        PreferenceUtils.saveLong(key, timestamp);
    }

    /**
     * 現在日時からlong型のタイムスタンプ作成
     * 
     * @return 現在のタイムスタンプ
     */
    private long getNowTimeStamp() {
        return new Date().getTime();
    }

    public void saveAnneiListTimeStamp() {
        saveTimeStamp(Const.TIMESTAMP_ANNEI_LIST_KEY, getNowTimeStamp());
    }

    /**
     * 安栄の一覧データをキャッシュに保存
     * 
     * @param result 一覧データ
     */
    public void saveAnneiList(Result result) {
        putResult(Const.PREF_ANNEI_LIST_KEY, result);
    }
}
