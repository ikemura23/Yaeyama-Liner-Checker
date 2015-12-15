
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
public class TimeTableKohamaView extends TimeTableBaseView {

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

    public TimeTableKohamaView(Context context) {
        super(context);
    }

    public TimeTableKohamaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_kohama, this);
        ButterKnife.bind(this, layout);
    }

    /***
     * 非表示一括切り替え用
     */
    private static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            // view.setEnabled(false);
            view.setVisibility(GONE);
        }
    };

    /***
     * 表示一括切り替え用
     */
    private static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            // view.setEnabled(value);
            view.setVisibility(VISIBLE);
        }
    };

    /**
     * 安栄 or 八重山観光フェリーのみの時刻表に切り替える
     * 
     * @param company
     */
    @Override
    public void changeViews(Company company) {

        if (company == Company.ANNEI) {
            // ViewUtils.setVisivilityViews(ykfViews, GONE);
            // ViewUtils.setVisivilityViews(aneiViews, VISIBLE);
            ButterKnife.apply(aneiViews, ENABLED, false);
            ButterKnife.apply(ykfViews, DISABLE);
        }
        else {
            // ViewUtils.setVisivilityViews(aneiViews, GONE);
            // ViewUtils.setVisivilityViews(ykfViews, VISIBLE);
            ButterKnife.apply(aneiViews, DISABLE);
            ButterKnife.apply(ykfViews, ENABLED, false);
        }
    }
}
