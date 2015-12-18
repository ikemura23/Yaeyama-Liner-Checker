
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf;

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
public class YkfTimeTableKohamaView extends TimeTableBaseView {

    // 安栄
    @Bind({
            R.id.kohama_anei_row2,
            R.id.kohama_anei_row4,
            R.id.kohama_anei_row6,
            R.id.kohama_anei_row8,
            R.id.kohama_anei_row10
    })
    List<View> aneiViews;

    // 八重山観光フェリー
    @Bind({
            R.id.kohama_ykf_row1,
            R.id.kohama_ykf_row3,
            R.id.kohama_ykf_row5,
            R.id.kohama_ykf_row7,
            R.id.kohama_ykf_row9,
            R.id.kohama_ykf_row11,
            R.id.kohama_ykf_row12,
    })
    List<View> ykfViews;

    public YkfTimeTableKohamaView(Context context) {
        super(context);
    }

    public YkfTimeTableKohamaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_kohama, this);
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
