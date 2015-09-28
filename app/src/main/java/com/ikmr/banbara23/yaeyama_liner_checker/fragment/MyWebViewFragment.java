
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.webkit.WebViewFragment;

/**
 * WebViewを表示するフラグメント
 */
public class MyWebViewFragment extends WebViewFragment {

    @Override
    public void onPause() {
        super.onPause();
        getWebView().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWebView().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getWebView().destroy();
    }
}
