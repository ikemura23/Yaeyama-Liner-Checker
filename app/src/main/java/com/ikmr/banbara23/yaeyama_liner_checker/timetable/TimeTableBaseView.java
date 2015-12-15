
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import butterknife.ButterKnife;

/**
 * 時刻表の継承元View
 */
public abstract class TimeTableBaseView extends LinearLayout {
    public TimeTableBaseView(Context context) {
        super(context);
        ButterKnife.bind(this);
    }

    public TimeTableBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void changeViews(Company company);
}
