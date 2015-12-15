
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表 竹富
 */
public class TimeTableHaterumaView extends TimeTableBaseView {

    @Bind(R.id.hateruma_row1)
    FrameLayout mRow1;

    @Bind(R.id.hateruma_row2)
    FrameLayout mRow2;

    @Bind(R.id.hateruma_row3)
    FrameLayout mRow3;

    public TimeTableHaterumaView(Context context) {
        super(context);
    }

    public TimeTableHaterumaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_hateruma, this);
        ButterKnife.bind(this, layout);
    }

    @Override
    public void switchViews(Company company) {

    }
}
