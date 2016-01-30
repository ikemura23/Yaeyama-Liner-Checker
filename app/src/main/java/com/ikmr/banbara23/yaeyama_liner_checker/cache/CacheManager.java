
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.text.TextUtils;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;

import java.util.Date;

import timber.log.Timber;

/**
 * キャッシュ管理マネージャー
 */
public class CacheManager {
    private static CacheManager sCacheManager;
    private static final String TAG = CacheManager.class.getSimpleName();

    public static CacheManager getInstance() {
        if (sCacheManager == null)
            sCacheManager = new CacheManager();
        return sCacheManager;
    }

    public boolean isNull(String key) {
        return TextUtils.isEmpty(PreferenceUtils.loadString(key));
    }

    public Result get(String key) {
        return JsonUtil.fromJson(PreferenceUtils.loadString(key));
    }

    public void putResult(String key, Result result) {
        PreferenceUtils.saveString(key, JsonUtil.toJsonFromResult(result));
    }

    /**
     * キャッシュの有効期限が切れているか？
     * 
     * @return true:切れてる false:切れてない
     */
    public boolean isExpiry() {
        if (invalidCache()) {
            Timber.d("キャッシュタイムスタンプが0以下");
            return true;
        }
        if (isNull(Const.PREF_ANNEI_LIST_KEY)) {
            Timber.d("キャッシュがnull");
            return true;
        }
        long duration = getDuration();
        Timber.d("duration:" + duration);
        if (0 > duration || duration > 3) {
            Timber.d("キャッシュ無効");
            return true;
        }
        Timber.d("キャッシュ有効");
        return false;
    }

    private boolean invalidCache() {
        return PreferenceUtils.loadLong(Const.TIMESTAMP_ANNEI_LIST_KEY) < 1;
    }

    private long loadAnneiListTimeStamp() {
        return PreferenceUtils.loadLong(Const.TIMESTAMP_ANNEI_LIST_KEY);
    }

    private long getDuration() {
        // DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
        // DateFormat.MEDIUM, Locale.getDefault());
        Date dateNow = new Date();

        // 日付をlong値に変換します。
        long saveTimeStamp = loadAnneiListTimeStamp();
        long nowTimeStamp = dateNow.getTime();

        Timber.d("saveTimeStamp:" + saveTimeStamp);
        Timber.d("nowTimeStamp:" + nowTimeStamp);

        // 差分の分を算出
        return (nowTimeStamp - saveTimeStamp) / (1000 * 60);
    }

    public void saveTimeStamp(String key, long timestamp) {
        Timber.d("nowTimeStamp:" + getNowTimeStamp());
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
