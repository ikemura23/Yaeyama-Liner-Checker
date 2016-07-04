
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * AdView用のベースフラグメント
 */
public class BaseDetailFragment extends BaseFragment {
    private AdView mAdView;

    public void initAdView(AdView adView) {
        mAdView = adView;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAdView == null) {
                    return;
                }
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }
}
