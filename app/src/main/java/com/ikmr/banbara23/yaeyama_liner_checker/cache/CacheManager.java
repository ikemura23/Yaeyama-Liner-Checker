
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.text.TextUtils;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
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

    public Result getResultCache(Company company) {
        String key = getResultCacheKey(company);
        return JsonUtil.fromJson(PreferenceUtils.loadString(key));
    }

    public void putResult(Company company, Result result) {
        String key = getResultCacheKey(company);
        PreferenceUtils.saveString(key, JsonUtil.toJsonFromResult(result));
    }

    /**
     * キャッシュの有効期限が切れているか？
     * 
     * @return true:切れてる false:切れてない
     */
    public boolean isExpiry(Company company) {
        String key = getTimeStampKey(company);
        if (key == null) {
            Timber.d("keyがnullなんですけど");
            return true;
        }
        if (invalidCache(key)) {
            Timber.d("キャッシュタイムスタンプが0以下");
            return true;
        }
        if (isNull(getResultCacheKey(company))) {
            Timber.d("一覧キャッシュ値がnull");
            return true;
        }
        long duration = getDuration(key);
        Timber.d("duration:" + duration);
        if (0 > duration || duration > 3) {
            Timber.d("期限切れでキャッシュ無効");
            return true;
        }
        Timber.d("キャッシュ有効");
        return false;
    }

    private boolean invalidCache(String key) {
        return PreferenceUtils.loadLong(key) < 1;
    }

    private long getDuration(String key) {
        // DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
        // DateFormat.MEDIUM, Locale.getDefault());
        Date dateNow = new Date();

        // 日付をlong値に変換します。
        long saveTimeStamp = PreferenceUtils.loadLong(key);
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

    public void saveNowTimeStamp(Company param) {
        String key = getTimeStampKey(param);
        saveTimeStamp(key, getNowTimeStamp());
    }

    /**
     * タイムスタンプのキー取得
     *
     * @return key
     */
    private String getTimeStampKey(Company company) {
        switch (company) {
            case ANNEI:
                return Const.TIMESTAMP_ANNEI_LIST_KEY;
            case YKF:
                return Const.TIMESTAMP_YKF_LIST_KEY;
            case DREAM:
                return Const.TIMESTAMP_DREAM_LIST_KEY;
            default:
                return null;
        }
    }

    /**
     * 一覧のキャッシュキー取得
     *
     * @return key
     */
    private String getResultCacheKey(Company company) {
        switch (company) {
            case ANNEI:
                return Const.PREF_ANNEI_LIST_KEY;
            case YKF:
                return Const.PREF_YKF_LIST_KEY;
            case DREAM:
                return Const.PREF_DREAM_LIST_KEY;
            default:
                return null;
        }
    }
}
