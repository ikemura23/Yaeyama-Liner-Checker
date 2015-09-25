
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * 一覧系画面の Fragment 共通のインタフェース
 */
public interface ListFragmentInterface {
    /** 初回検索時 */
    void onResetQuery(String newVersion);

    /** 検索開始時 */
    void onStartQuery(String version);

    /** API結果取得時 */
    void onResultQuery(String version, int count, int total);

    /** APIエラー時 */
    void onFailedQuery(Exception error);

    /** API 終了時 */
    void onFinishQuery();
}