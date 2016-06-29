
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * AdView用のベースフラグメント
 */
public class BaseDetailFragment extends BaseFragment {
    public AdView mAdView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initAdView();
            }
        });
    }

    private void initAdView() {
        if (mAdView == null) {
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
