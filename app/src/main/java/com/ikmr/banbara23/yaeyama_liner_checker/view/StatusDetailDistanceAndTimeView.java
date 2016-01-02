
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
 * 距離と時間のカスタムビュー
 */
public class StatusDetailDistanceAndTimeView extends FrameLayout {

    @Bind(R.id.view_status_detail_top_distance_text)
    TextView mDistanceText;

    @Bind(R.id.view_status_detail_top_time_text)
    TextView mTimeText;

    public StatusDetailDistanceAndTimeView(Context context) {
        super(context);
    }

    public StatusDetailDistanceAndTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail_distance_time, this);
        ButterKnife.bind(this, layout);
    }

    public void setDistanceText(String text) {
        if (text == null) {
            mDistanceText.setVisibility(GONE);
            return;
        }
        mDistanceText.setText(text);
    }

    public void setTimeText(String text) {
        if (text == null) {
            mTimeText.setVisibility(GONE);
            return;
        }
        mTimeText.setText(text);
    }
}
