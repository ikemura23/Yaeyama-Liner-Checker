
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * ステータスリストのカスタムビュー
 */
public class StatusListView extends LinearLayout {
    TextView mNameText;

    public StatusListView(Context context) {
        super(context);
    }

    public StatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameText = (TextView) findViewById(R.id.view_status_list_text);
    }

    public void bind(String value) {
        mNameText.setText(value);
    }
}
