
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.ButterKnife;

/**
 * ドリーム観光 時刻表 上原・鳩間
 */
public class DreamTimeTableUeharaView extends DreamTimeTableBaseView {

    public DreamTimeTableUeharaView(Context context) {
        super(context);
    }

    public DreamTimeTableUeharaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_dream_uehara, this);
        ButterKnife.bind(this, layout);
    }
}
