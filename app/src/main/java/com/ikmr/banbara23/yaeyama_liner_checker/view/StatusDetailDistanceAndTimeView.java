
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 距離と時間のカスタムビュー
 */
public class StatusDetailDistanceAndTimeView extends FrameLayout {

    @Bind(R.id.view_status_detail_top_distance_text)
    TextView mDistanceText;

    @Bind(R.id.view_status_detail_top_time_text)
    TextView mTimeText;

    @Bind(R.id.view_status_detail_top_distance_layout)
    LinearLayout mDistanceLayout;

    public StatusDetailDistanceAndTimeView(Context context) {
        super(context);
    }

    public StatusDetailDistanceAndTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_distance_time, this);
        ButterKnife.bind(this, layout);
    }

    /**
     * 走行距離
     * 
     * @param text
     */
    public void setDistanceText(String text) {
        if (text == null) {
            mDistanceLayout.setVisibility(GONE);
            return;
        }
        mDistanceText.setText(text);
    }

    /**
     * 走行時間
     * 
     * @param text
     */
    public void setTimeText(String text) {
        if (text == null) {
            mTimeText.setVisibility(GONE);
            return;
        }
        mTimeText.setText(text);
    }
}
