
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
public class DreamTimeTableKuroshimaView extends TimeTableBaseView {

    // 安栄
    @Bind({
            R.id.kuroshima_anei_row2,
            R.id.kuroshima_anei_row5,
    })
    List<View> aneiViews;

    // 八重山観光フェリー
    @Bind({
            R.id.kuroshima_ykf_row1,
            R.id.kuroshima_ykf_row3,
            R.id.kuroshima_ykf_row4,
    })
    List<View> ykfViews;

    public DreamTimeTableKuroshimaView(Context context) {
        super(context);
    }

    public DreamTimeTableKuroshimaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_kuroshima, this);
        ButterKnife.bind(this, layout);
    }

    /**
     * 安栄 or 八重山観光フェリーのみの時刻表に切り替える
     *
     * @param company
     */
    @Override
    public void switchViews(Company company) {
        switcCompany(company, aneiViews, ykfViews);
    }
}
