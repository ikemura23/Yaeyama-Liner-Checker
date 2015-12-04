
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * 時刻表 竹富
 */
public class TimeTableHaterumaView extends LinearLayout {

    public TimeTableHaterumaView(Context context) {
        super(context);
    }

    public TimeTableHaterumaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_hateruma, this);
    }
}