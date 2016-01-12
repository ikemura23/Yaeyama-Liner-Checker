
package com.ikmr.banbara23.yaeyama_liner_checker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

import butterknife.BindColor;
import butterknife.BindString;

/**
 * ステータスリストのカスタムビュー
 */
public class StatusListView extends FrameLayout {

    // @Bind(R.id.view_status_list_status_icon_text)
    TextView mStatusIconText;
    // @Bind(R.id.view_status_list_port_text)
    TextView mPortText;
    // @Bind(R.id.view_status_list_description_text)
    TextView mDescriptionText;

    // Bind String ---------------------------------------
    @BindString(R.string.status_normal)
    String normal;

    @BindString(R.string.status_cation)
    String cation;

    @BindString(R.string.status_cancel)
    String cancel;

    @BindString(R.string.status_cancel)
    String suspend;

    // BindColor ---------------------------------------
    @BindColor(R.color.status_normal)
    int colorNormal;
    @BindColor(R.color.status_cation)
    int colorCation;
    @BindColor(R.color.status_cancel)
    int colorCancel;
    @BindColor(R.color.dark_grey)
    int colorSuspend;

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
        mStatusIconText = (TextView) findViewById(R.id.view_status_list_status_icon_text);
        mDescriptionText = (TextView) findViewById(R.id.view_status_list_description_text);
    }

    /**
     * ビューに値を表示
     *
     * @param liner
     */
    public void bind(Liner liner) {
        setPort(liner.getPort());
        setStatus(liner.getStatus());
        setDescription(liner.getText());
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

        switch (status) {
            case NORMAL:
                mStatusIconText.setText(getContext().getString(R.string.status_normal));
                mStatusIconText.setTextColor(getContext().getColor(R.color.status_normal));
                break;
            case CANCEL:
                mStatusIconText.setText(cancel);
                mStatusIconText.setTextColor(colorCancel);
                break;
            case CAUTION:
                mStatusIconText.setText(cation);
                mStatusIconText.setTextColor(colorCation);
                break;
            case SUSPEND:
                mStatusIconText.setText(suspend);
                mStatusIconText.setTextColor(colorSuspend);
        }
    }

    /**
     * コメント
     *
     * @param text
     */
    private void setDescription(String text) {
        if (text == null) {
            mDescriptionText.setVisibility(GONE);
            return;
        }
        mDescriptionText.setText(text);
    }
}
