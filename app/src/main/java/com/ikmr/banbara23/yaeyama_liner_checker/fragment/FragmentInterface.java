
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;

/**
 * 一覧系画面の Fragment 共通のインタフェース
 */
public interface FragmentInterface {
    /** 初回検索時 */
    void onResetQuery();

    /** 検索開始時 */
    void onStartQuery();

    /** API結果取得時 */
    void onResultQuery(Liner liner, String value);

    /** APIエラー時 */
    void onFailedQuery();

    /** API 終了時 */
    void onFinishQuery();
}
