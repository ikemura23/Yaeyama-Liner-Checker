
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

public class CacheHelper {
    /**
     * 一覧のタイムスタンプのキー取得
     *
     * @return key 一覧のタイムスタンプkey
     */
    protected static String getListTimeStampKey(Company company) {
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
    protected static String getResultCacheKey(Company company) {
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

    /**
     * 一覧のキャッシュキー取得
     *
     * @return key
     */
    protected static String getResultDetailCacheKey(Port port) {
        switch (port) {
            case TAKETOMI:
                return Const.PREF_ANNEI_DETAIL_TAKETOMI_KEY;
            case KOHAMA:
                return Const.PREF_ANNEI_DETAIL_KOHAMA_KEY;
            case KUROSHIMA:
                return Const.PREF_ANNEI_DETAIL_KUROSHIMA_KEY;
            case OOHARA:
                return Const.PREF_ANNEI_DETAIL_OOHARA_KEY;
            case UEHARA:
                return Const.PREF_ANNEI_DETAIL_UEHARA_KEY;
            case HATOMA:
                return Const.PREF_ANNEI_DETAIL_HATOMA_KEY;
            case HATERUMA:
                return Const.PREF_ANNEI_DETAIL_HATERUMA_KEY;
            default:
                return null;
        }
    }

    /**
     * 一覧のキャッシュキー取得
     *
     * @return key
     */
    protected static String getResultDetailTimeStampKey(Port port) {
        switch (port) {
            case TAKETOMI:
                return Const.TIMESTAMP_ANNEI_DETAIL_TAKETOMI_KEY;
            case KOHAMA:
                return Const.TIMESTAMP_ANNEI_DETAIL_KOHAMA_KEY;
            case KUROSHIMA:
                return Const.TIMESTAMP_ANNEI_DETAIL_KUROSHIMA_KEY;
            case OOHARA:
                return Const.TIMESTAMP_ANNEI_DETAIL_OOHARA_KEY;
            case UEHARA:
                return Const.TIMESTAMP_ANNEI_DETAIL_UEHARA_KEY;
            case HATOMA:
                return Const.TIMESTAMP_ANNEI_DETAIL_HATOMA_KEY;
            case HATERUMA:
                return Const.TIMESTAMP_ANNEI_DETAIL_HATERUMA_KEY;
            default:
                return null;
        }
    }
}
