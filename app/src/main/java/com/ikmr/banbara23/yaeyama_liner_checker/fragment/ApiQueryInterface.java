
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

/**
 * Created by ikemurakazutaka on 2015/09/28.
 */
public interface ApiQueryInterface {
    /** Fragmentの通信処理が開始した事をActivityに通知する目的に使用 **/
    void startQuery();

    /** Fragmentの通信処理が終了した事をActivityに通知する目的に使用 **/
    void finishQuery();
}
