
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ドリーム観光 料金のカスタムビュー
 */
public class StatusDetailPriceDreamView extends FrameLayout {

    @Bind(R.id.view_status_detail_price_dream_liner_layout)
    LinearLayout mPriceDreamLinerLayout;
    @Bind(R.id.view_status_detail_price_dream_liner_adult_text)
    TextView mPriceDreamLinerAdultText;
    @Bind(R.id.view_status_detail_top_price_dream_liner_child_text)
    TextView mPriceDreamLinerChildText;

    @Bind(R.id.view_status_detail_price_dream_ferry_layout)
    LinearLayout mPriceDreamFerryLayout;
    @Bind(R.id.view_status_detail_price_ferry_adult_text)
    TextView mPriceFerryAdultText;
    @Bind(R.id.view_status_detail_top_price_ferry_child_text)
    TextView mPriceFerryChildText;

    public StatusDetailPriceDreamView(Context context) {
        super(context);
    }

    public StatusDetailPriceDreamView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_price_dream, this);
        ButterKnife.bind(this, layout);
    }

    /**
     * 高速船
     *
     * @param price 高速船の値段
     */
    public void setLinerPrice(Price price) {
        if (price == null || (TextUtils.isEmpty(price.getAdult()) && TextUtils.isEmpty(price.getChild()))) {
            mPriceDreamLinerLayout.setVisibility(GONE);
            return;
        }
        setText(mPriceDreamLinerAdultText, price.getAdult());
        setText(mPriceDreamLinerChildText, price.getChild());
    }

    /**
     * フェリー
     *
     * @param price フェリーの値段
     */
    public void setFerryPrice(Price price) {
        if (price == null) {
            mPriceDreamFerryLayout.setVisibility(GONE);
            return;
        }
        setText(mPriceFerryAdultText, price.getAdult());
        setText(mPriceFerryChildText, price.getChild());
    }

    /**
     * 汎用 textviewに値セット
     * 
     * @param textView 表示したいtextview
     * @param value 表示値
     */
    private void setText(TextView textView, String value) {
        if (textView == null || value == null) {
            return;
        }
        textView.setText(value);
    }
}
