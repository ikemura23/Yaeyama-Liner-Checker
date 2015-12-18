
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableBaseView;

import butterknife.ButterKnife;

/**
 * 時刻表 竹富
 */
public class AnneiTimeTableOoharaView extends YkfTimeTableBaseView {

    public AnneiTimeTableOoharaView(Context context) {
        super(context);
    }

    public AnneiTimeTableOoharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_annei_oohara, this);
        ButterKnife.bind(this, layout);
    }
}
