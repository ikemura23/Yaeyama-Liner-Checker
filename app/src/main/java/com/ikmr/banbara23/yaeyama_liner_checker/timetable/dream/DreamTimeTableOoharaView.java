
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.ButterKnife;

/**
 * ドリーム観光 時刻表 大原
 */
public class DreamTimeTableOoharaView extends DreamTimeTableBaseView {

    public DreamTimeTableOoharaView(Context context) {
        super(context);
    }

    public DreamTimeTableOoharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_dream_oohara, this);
        ButterKnife.bind(this, layout);
    }
}
