
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.ButterKnife;

/**
 * ドリーム観光 料金のカスタムビュー
 */
public class StatusDetailPriceDreamOoharaView extends FrameLayout {

    public StatusDetailPriceDreamOoharaView(Context context) {
        super(context);
    }

    public StatusDetailPriceDreamOoharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_price_dream_oohara, this);
        ButterKnife.bind(this, layout);
    }
}
