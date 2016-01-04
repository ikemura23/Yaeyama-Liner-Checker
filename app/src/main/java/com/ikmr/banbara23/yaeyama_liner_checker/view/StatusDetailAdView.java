
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * カードUI版の広告
 */
public class StatusDetailAdView extends FrameLayout {
    @Bind(R.id.adView)
    AdView mAdView;

    public StatusDetailAdView(Context context) {
        super(context);
    }

    public StatusDetailAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_card_ad_view, this);
        ButterKnife.bind(view, this);
        loadAd();
    }

    protected void loadAd() {
        try {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } catch (Exception e) {
        }
    }
}
