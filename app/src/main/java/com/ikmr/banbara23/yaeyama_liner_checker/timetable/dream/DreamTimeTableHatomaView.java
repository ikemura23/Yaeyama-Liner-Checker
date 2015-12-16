
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableBaseView;

/**
 * 時刻表 竹富
 */
public class DreamTimeTableHatomaView extends TimeTableBaseView {

    public DreamTimeTableHatomaView(Context context) {
        super(context);
    }

    public DreamTimeTableHatomaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_hatoma, this);
    }

    @Override
    public void switchViews(Company company) {

    }
}
