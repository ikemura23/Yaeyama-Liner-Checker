
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * 詳細のカスタムビュー
 */
public class StatusDetailView extends LinearLayout {
    TextView mValueText;

    public StatusDetailView(Context context) {
        super(context);
    }

    public StatusDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail, this);
        mValueText = (TextView) layout.findViewById(R.id.view_status_detail_value);
    }

    public void bind(String value) {
        if (value == null) {
            return;
        }
        mValueText.setText(value);
    }

}
