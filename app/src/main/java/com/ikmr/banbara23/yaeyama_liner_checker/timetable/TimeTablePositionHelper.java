package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.cache.PreferenceUtils;

/**
 * 時刻表のタブ位置、スピナー位置を記憶、呼び出すヘルパークラス
 */
public class TimeTablePositionHelper {

    /**
     * 選択タブインデックスを取得
     *
     * @return 選択すべきタブ番号
     */
    public static int getInitTabPosition() {
        return PreferenceUtils.loadInt(Base.getContext().getString(R.string.time_table_init_tab_value));
    }

    /**
     * タブインデックスを記憶
     *
     * @param currentPosition 現在のタブ位置
     */
    public static void setCurrentTabPosition(int currentPosition) {
        PreferenceUtils.saveInt(Base.getContext().getString(R.string.time_table_init_tab_value), currentPosition);
    }

    /**
     * 港スピナーのインデックスを取得
     *
     * @return
     */
    public static int getInitSpinnerPosition() {
        return PreferenceUtils.loadInt(Base.getContext().getString(R.string.time_table_init_spinner_value));
    }

    /**
     * 港スピナーのインデックスを保存
     *
     * @param currentPosition
     */
    public static void setCurrentSpinnerPosition(int currentPosition) {
        PreferenceUtils.saveInt(Base.getContext().getString(R.string.time_table_init_spinner_value), currentPosition);
    }
}
