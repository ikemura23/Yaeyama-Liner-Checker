
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
public class DreamTimeTableTaketomiView extends TimeTableBaseView {

    // 安栄
    @Bind({
            R.id.taketomi_anei_row1,
            R.id.taketomi_anei_row3,
            R.id.taketomi_anei_row5,
            R.id.taketomi_anei_row7,
            R.id.taketomi_anei_row9,
            R.id.taketomi_anei_row11,
            R.id.taketomi_anei_row12,
            R.id.taketomi_anei_row15,
            R.id.taketomi_anei_row17
    })
    List<View> aneiViews;

    // 八重山観光フェリー
    @Bind({
            R.id.taketomi_ykf_row2,
            R.id.taketomi_ykf_row4,
            R.id.taketomi_ykf_row6,
            R.id.taketomi_ykf_row8,
            R.id.taketomi_ykf_row10,
            R.id.taketomi_ykf_row13,
            R.id.taketomi_ykf_row14,
            R.id.taketomi_ykf_row16,
            R.id.taketomi_ykf_row18
    })
    List<View> ykfViews;

    public DreamTimeTableTaketomiView(Context context) {
        super(context);
    }

    public DreamTimeTableTaketomiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_taketomi, this);
        ButterKnife.bind(this, layout);
    }

    @Override
    public void switchViews(Company company) {
        switcCompany(company, aneiViews, ykfViews);
    }
}
