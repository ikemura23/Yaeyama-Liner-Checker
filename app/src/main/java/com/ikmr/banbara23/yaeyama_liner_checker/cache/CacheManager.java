
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
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
    private static final long RESET = -1;

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
        try {
            String key = getTimeStampKey(company);
            if (key == null) {
                Timber.d(company.getCompanyName() + ":キャッシュ無効 keyがnullなんですけど!!");
                return true;
            }
            if (invalidCache(key)) {
                Timber.d(company.getCompanyName() + ":キャッシュ無効 タイムスタンプが0以下");
                return true;
            }
            if (isNull(getResultCacheKey(company))) {
                Timber.d(company.getCompanyName() + ":キャッシュ無効 一覧キャッシュ値がnull");
                return true;
            }
            long duration = getDuration(key);
            Timber.d("duration:" + duration);
            if (0 > duration || duration > 3) {
                Timber.d(company.getCompanyName() + ":キャッシュ無効 期限切れ");
                return true;
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
            return true;
        }
        return false;
    }

    private boolean invalidCache(String key) {
        return PreferenceUtils.loadLong(key) < 1;
    }

    /**
     * 現在時刻のタイムスタンプと保存済みのタイムスタンプの差分を計算して返す
     * 
     * @param key 観光会社の一覧用タイムスタンプ
     * @return 差分(分)
     */
    private long getDuration(String key) {
        try {
            Date dateNow = new Date();

            // 日付をlong値に変換します。
            long saveTimeStamp = PreferenceUtils.loadLong(key);
            long nowTimeStamp = dateNow.getTime();

            Timber.d(key + " saveTimeStamp:" + saveTimeStamp);
            Timber.d(key + " nowTimeStamp:" + nowTimeStamp);

            // 差分の分を算出
            return (nowTimeStamp - saveTimeStamp) / (1000 * 60);

        } catch (Exception e) {
            Crashlytics.logException(e);
            return RESET;
        }
    }

    /**
     * 引数の値をタイムスタンプとして保存
     * 
     * @param key 保存キー
     * @param timestamp 保存タイムスタンプ値
     */
    public void saveTimeStamp(String key, long timestamp) {
        Timber.d(key + "nowTimeStamp:" + getNowTimeStamp());
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

    public void resetTimeStamp(Company param) {
        String key = getTimeStampKey(param);
        saveTimeStamp(key, 0);
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
