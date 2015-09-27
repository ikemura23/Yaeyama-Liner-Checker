
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * ステータスリストのカスタムビュー
 */
public class StatusListView extends LinearLayout {
    TextView mPortText;
    TextView mStatusText;
    TextView mCommentText;

    public StatusListView(Context context) {
        super(context);
    }

    public StatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPortText = (TextView) findViewById(R.id.view_status_list_port_text);
        mStatusText = (TextView) findViewById(R.id.view_status_list_status_text);
        mCommentText = (TextView) findViewById(R.id.view_status_list_comment_text);
    }

    public void bind(Liner liner) {
        mPortText.setText(liner.getPort().getValue());
        mStatusText.setText(liner.getStatus().getStatus());
        mCommentText.setText(liner.getText());
    }
}
