
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表 竹富
 */
public class TimeTableOoharaView extends TimeTableBaseView {

    // 安栄
    @Bind({
            R.id.oohara_anei_row3,
            R.id.oohara_anei_row5,
            R.id.oohara_anei_row6,
            R.id.oohara_anei_row8,
            R.id.oohara_anei_row11
    })
    List<View> aneiViews;

    // 八重山観光フェリー
    @Bind({
            R.id.oohara_ykf_row1,
            R.id.oohara_ykf_row2,
            R.id.oohara_ykf_row4,
            R.id.oohara_ykf_row7,
            R.id.oohara_ykf_row9,
            R.id.oohara_ykf_row10,
            R.id.oohara_ykf_row12,
    })
    List<View> ykfViews;

    public TimeTableOoharaView(Context context) {
        super(context);
    }

    public TimeTableOoharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_oohara, this);
        ButterKnife.bind(this, layout);
    }

    @Override
    public void switchViews(Company company) {
        switcCompany(company, aneiViews, ykfViews);
    }
}
