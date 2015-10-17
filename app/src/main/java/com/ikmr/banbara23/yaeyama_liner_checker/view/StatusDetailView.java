
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;

/**
 * 詳細のカスタムビュー
 */
public class StatusDetailView extends LinearLayout {
    TextView mUpdateText;
    TextView mValueText;

    public StatusDetailView(Context context) {
        super(context);
    }

    public StatusDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_status_detail, this);
        mUpdateText = (TextView) layout.findViewById(R.id.view_status_update_text);
        mValueText = (TextView) layout.findViewById(R.id.view_status_detail_value);
    }

    public void bind(Liner liner, String value) {
        setUpdateText(liner);
        setValue(value);
    }

    private void setUpdateText(Liner liner) {
        mUpdateText.setText(liner.getText());
    }

    private void setValue(String value) {
        if (value == null) {
            return;
        }
        mValueText.setText(value);
    }

}
