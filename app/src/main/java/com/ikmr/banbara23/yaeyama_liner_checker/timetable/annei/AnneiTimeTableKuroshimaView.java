
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableBaseView;

/**
 * 時刻表 竹富
 */
public class AnneiTimeTableKuroshimaView extends YkfTimeTableBaseView {

    public AnneiTimeTableKuroshimaView(Context context) {
        super(context);
    }

    public AnneiTimeTableKuroshimaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_annei_kuroshima, this);
        ButterKnife.bind(this, layout);
    }
}
