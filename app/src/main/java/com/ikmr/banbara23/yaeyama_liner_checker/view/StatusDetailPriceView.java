
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 料金のカスタムビュー
 */
public class StatusDetailPriceView extends FrameLayout {

    @Bind(R.id.view_status_detail_price_adult)
    TextView mPriceAdultText;
    @Bind(R.id.view_status_detail_top_price_child)
    TextView mPriceChildText;
    @Bind(R.id.view_status_detail_price_handicapped)
    TextView mPriceHandicappedText;

    public StatusDetailPriceView(Context context) {
        super(context);
    }

    public StatusDetailPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_price, this);
        ButterKnife.bind(this, layout);
    }

    public void setPriceAdultText(String text) {
        if (text == null) {
            mPriceAdultText.setVisibility(GONE);
            return;
        }
        mPriceAdultText.setText(text);
    }

    public void setPriceChildText(String text) {
        if (text == null) {
            mPriceChildText.setVisibility(GONE);
            return;
        }
        mPriceChildText.setText(text);
    }

    public void setPriceHandicappedText(String text) {
        if (text == null) {
            mPriceHandicappedText.setVisibility(GONE);
            return;
        }
        mPriceHandicappedText.setText(text);
    }
}
