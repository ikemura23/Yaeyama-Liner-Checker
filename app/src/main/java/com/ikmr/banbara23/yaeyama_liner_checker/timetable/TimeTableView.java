
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class TimeTableView extends LinearLayout {

    private Port mPort;

    @Bind(R.id.view_time_table_hateruma)
    FrameLayout mHateruma;

    @Bind({
            R.id.view_time_table_hateruma,
            R.id.view_time_table_hatoma,
            R.id.view_time_table_kohama,
            R.id.view_time_table_kurhoshima,
            R.id.view_time_table_oohara,
            R.id.view_time_table_taketomi,
            R.id.view_time_table_uehara
    })
    List<EditText> timeTableViews;

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_hateruma, this);
        ButterKnife.bind(this, layout);
    }

    private void setPort(Port port) {
        mPort = port;
    }
}
