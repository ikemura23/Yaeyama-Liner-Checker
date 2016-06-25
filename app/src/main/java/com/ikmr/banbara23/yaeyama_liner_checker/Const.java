
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * 定数クラス
 */
public class Const {
    // 通信全般のタイムアウト 10秒, Jsoupのデフォルトは3秒
    // http://jsoup.org/apidocs/org/jsoup/Connection.html
    public static final int CONNECTION_TIME_OUT = 10000;

    // 一覧の値
    public static final String PREF_ANNEI_LIST_KEY = "PREF_ANNEI_LIST_KEY";
    public static final String PREF_YKF_LIST_KEY = "PREF_YKF_LIST_KEY";
    public static final String PREF_DREAM_LIST_KEY = "PREF_DREAM_LIST_KEY";
    public static final String PREF_ANNEI_DETAIL_KEY = "PREF_ANNEI_DETAIL_KEY";

    // 一覧のタイムスタンプ
    public static final String TIMESTAMP_ANNEI_LIST_KEY = "TIMESTAMP_ANNEI_LIST_KEY";
    public static final String TIMESTAMP_YKF_LIST_KEY = "TIMESTAMP_YKF_LIST_KEY";
    public static final String TIMESTAMP_DREAM_LIST_KEY = "TIMESTAMP_DREAM_LIST_KEY";

    // 設定画面で設定するキャッシュ設定値
    public static final String CACHE_CHECKBOX_PREFERENCE = "cache_checkbox_preference";

    // 詳細の値
    public static final String PREF_ANNEI_DETAIL_TAKETOMI_KEY = "PREF_ANNEI_DETAIL_TAKETOMI_KEY";
    public static final String PREF_ANNEI_DETAIL_KOHAMA_KEY = "PREF_ANNEI_DETAIL_KOHAMA_KEY";
    public static final String PREF_ANNEI_DETAIL_KUROSHIMA_KEY = "PREF_ANNEI_DETAIL_KUROSHIMA_KEY";
    public static final String PREF_ANNEI_DETAIL_OOHARA_KEY = "PREF_ANNEI_DETAIL_OOHARA_KEY";
    public static final String PREF_ANNEI_DETAIL_UEHARA_KEY = "PREF_ANNEI_DETAIL_UEHARA_KEY";
    public static final String PREF_ANNEI_DETAIL_HATOMA_KEY = "PREF_ANNEI_DETAIL_HATOMA_KEY";
    public static final String PREF_ANNEI_DETAIL_HATERUMA_KEY = "PREF_ANNEI_DETAIL_HATERUMA_KEY";

    // 詳細のタイムスタンプ
    public static final String TIMESTAMP_ANNEI_DETAIL_TAKETOMI_KEY = "TIMESTAMP_ANNEI_DETAIL_TAKETOMI_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_KOHAMA_KEY = "TIMESTAMP_ANNEI_DETAIL_KOHAMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_KUROSHIMA_KEY = "TIMESTAMP_ANNEI_DETAIL_KUROSHIMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_OOHARA_KEY = "TIMESTAMP_ANNEI_DETAIL_OOHARA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_UEHARA_KEY = "TIMESTAMP_ANNEI_DETAIL_UEHARA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_HATOMA_KEY = "TIMESTAMP_ANNEI_DETAIL_HATOMA_KEY";
    public static final String TIMESTAMP_ANNEI_DETAIL_HATERUMA_KEY = "TIMESTAMP_ANNEI_DETAIL_HATERUMA_KEY";

    // キャッシュの保存時間(分)
    public static final int SAVE_TIME = 3;

    // FireBase Analytics
    public class FireBaseAnalitycsTag {
        public static final String TOP = "top";
        public static final String OTHER = "other";
        public static final String SETTING = "setting";
        public static final String STATUS_LIST = "status_list";
        public static final String STATUS_DETAIL_ANNEI = "status_detail_annei";
        public static final String STATUS_DETAIL_YKF = "status_detail_ykf";
        public static final String STATUS_DETAIL_DREAM = "status_detail_dream";
        public static final String WEATHER = "weather";
        public static final String TIME_TABLE = "time_table";
        public static final String TIME_TABLE_SPINNER = "time_table_spinner";
    }
}
