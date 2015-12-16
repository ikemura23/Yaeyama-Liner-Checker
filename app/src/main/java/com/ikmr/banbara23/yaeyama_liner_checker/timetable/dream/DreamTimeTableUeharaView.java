
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableBaseView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表 竹富
 */
public class DreamTimeTableUeharaView extends TimeTableBaseView {

    // 安栄
    @Bind({
            R.id.uehara_anei_row1,
            R.id.uehara_anei_row2,
            R.id.uehara_anei_row4,
            R.id.uehara_anei_row5,
            R.id.uehara_anei_row7
    })
    List<View> aneiViews;

    // 八重山観光フェリー
    @Bind({
            R.id.uehara_ykf_row3,
            R.id.uehara_ykf_row6
    })
    List<View> ykfViews;

    public DreamTimeTableUeharaView(Context context) {
        super(context);
    }

    public DreamTimeTableUeharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_uehara, this);
        ButterKnife.bind(this, layout);
    }

    @Override
    public void switchViews(Company company) {
        switcCompany(company, aneiViews, ykfViews);
    }
}
