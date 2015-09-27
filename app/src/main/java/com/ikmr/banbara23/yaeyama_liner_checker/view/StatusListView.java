
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

/**
 * ステータスリストのカスタムビュー
 */
public class StatusListView extends LinearLayout {
    TextView mPortText;
    TextView mStatus_normal;
    TextView mStatus_cancel;
    TextView mStatus_cation;
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
        mStatus_normal = (TextView) findViewById(R.id.view_status_list_status_normal_text);
        mStatus_cancel = (TextView) findViewById(R.id.view_status_list_status_cancel_text);
        mStatus_cation = (TextView) findViewById(R.id.view_status_list_status_cation_text);
        mCommentText = (TextView) findViewById(R.id.view_status_list_comment_text);
    }

    /**
     * ビューに値を表示
     * 
     * @param liner
     */
    public void bind(Liner liner) {
        setPort(liner.getPort());
        setStatus(liner.getStatus());
        setComment(liner.getText());
    }

    /**
     * 港
     * 
     * @param port
     */
    private void setPort(Port port) {
        if (port == null) {
            return;
        }
        mPortText.setText(port.getValue());
    }

    /**
     * 運航状況
     *
     * @param status
     */
    private void setStatus(Status status) {
        if (status == null) {
            return;
        }
        mStatus_normal.setVisibility(GONE);
        mStatus_cancel.setVisibility(GONE);
        mStatus_cation.setVisibility(GONE);

        switch (status) {
            case NORMAL:
                mStatus_normal.setVisibility(VISIBLE);
                break;
            case CANCEL:
                mStatus_cancel.setVisibility(VISIBLE);
                break;
            case CAUTION:
                mStatus_cation.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * コメント
     *
     * @param text
     */
    private void setComment(String text) {
        if (text == null) {
            mCommentText.setText("エラー");
            return;
        }
        mCommentText.setText(text);
    }
}
