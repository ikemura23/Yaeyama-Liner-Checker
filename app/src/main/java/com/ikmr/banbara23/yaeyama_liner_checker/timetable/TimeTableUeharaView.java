
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

/**
 * 時刻表 竹富
 */
public class TimeTableUeharaView extends TimeTableBaseView {

    public TimeTableUeharaView(Context context) {
        super(context);
    }

    public TimeTableUeharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_uehara, this);
    }

    @Override
    public void switchViews(Company company) {

    }
}
