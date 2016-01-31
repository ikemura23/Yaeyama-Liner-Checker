
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
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

    public Result getListResultCache(Company company) {
        String key = CacheHelper.getResultCacheKey(company);
        return JsonUtil.fromJson(PreferenceUtils.loadString(key));
    }

    public String getDetailAnneiResultCache() {
        return PreferenceUtils.loadString(Const.PREF_ANNEI_DETAIL_KEY);
    }

    public void putResult(Company company, Result result) {
        String key = CacheHelper.getResultCacheKey(company);
        PreferenceUtils.saveString(key, JsonUtil.toJsonFromResult(result));
    }

    public void putDetail(Port port, String comment) {
        PreferenceUtils.saveString(CacheHelper.getResultDetailCacheKey(port), comment);
    }

    /**
     * キャッシュの有効期限が切れているか？
     * 
     * @return true:切れてる false:切れてない
     */
    public boolean isExpiryList(Company company) {
        try {
            String key = CacheHelper.getListTimeStampKey(company);
            if (key == null) {
                Timber.d(company.getCompanyName() + ":一覧キャッシュ無効 keyがnullなんですけど!!");
                return true;
            }
            if (invalidTimeStamp(key)) {
                Timber.d(company.getCompanyName() + ":一覧キャッシュ無効 タイムスタンプが0以下");
                return true;
            }
            if (isNull(CacheHelper.getResultCacheKey(company))) {
                Timber.d(company.getCompanyName() + ":一覧キャッシュ無効 キャッシュ値がnull");
                return true;
            }
            long duration = getDuration(key);
            Timber.d("duration list:" + duration);
            if (0 > duration || duration > 3) {
                Timber.d(company.getCompanyName() + ":一覧キャッシュ無効 期限切れ");
                return true;
            }
            Timber.d(company.getCompanyName() + ":一覧キャッシュ有効");
        } catch (Exception e) {
            Crashlytics.logException(e);
            return true;
        }
        return false;
    }

    /**
     * キャッシュの有効期限が切れているか？
     *
     * @return true:切れてる false:切れてない
     * @param port
     */
    public boolean isExpiryDetailAnnei(Port port) {
        try {
            String timeStampKey = CacheHelper.getResultDetailTimeStampKey(port);
            if (invalidTimeStamp(timeStampKey)) {
                Timber.d(Company.ANNEI.getCompanyName() + ":詳細キャッシュ無効 タイムスタンプが0以下 " + port.getPortSimple());
                return true;
            }
            String valueKey = CacheHelper.getResultDetailCacheKey(port);
            if (isNull(valueKey)) {
                Timber.d(Company.ANNEI.getCompanyName() + ":詳細キャッシュ無効 一覧キャッシュ値がnull) " + port.getPortSimple());
                return true;
            }
            long duration = getDuration(timeStampKey);
            Timber.d("duration detail:" + duration);
            if (0 > duration || duration > 3) {
                Timber.d(Company.ANNEI.getCompanyName() + ":詳細キャッシュ無効 期限切れ " + port.getPortSimple());
                return true;
            }
            Timber.d(Company.ANNEI.getCompanyName() + ":詳細キャッシュ有効 " + port.getPortSimple());
        } catch (Exception e) {
            Crashlytics.logException(e);
            return true;
        }
        return false;
    }

    /**
     * タイムスタンプが0以下で無効か？
     * 
     * @param key
     * @return
     */
    private boolean invalidTimeStamp(String key) {
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
        Timber.d(key + " nowTimeStamp:" + getNowTimeStamp());
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

    /**
     * 現在時刻のタイムスタンプを保存
     * 
     * @param param 観光会社
     */
    public void saveNowListTimeStamp(Company param) {
        String key = CacheHelper.getListTimeStampKey(param);
        saveTimeStamp(key, getNowTimeStamp());
    }

    public void saveNowDetailAnneiTimeStamp(Port port) {
        saveTimeStamp(CacheHelper.getResultDetailTimeStampKey(port), getNowTimeStamp());
    }

    /**
     * タイムスタンプをリセット
     * 
     * @param param 観光会社
     */
    public void resetTimeStamp(Company param) {
        String key = CacheHelper.getListTimeStampKey(param);
        saveTimeStamp(key, 0);
    }

}
