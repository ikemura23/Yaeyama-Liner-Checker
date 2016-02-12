
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * 時刻表 竹富
 */
public class AnneiTimeTableHatomaView extends LinearLayout {

    public AnneiTimeTableHatomaView(Context context) {
        super(context);
    }

    public AnneiTimeTableHatomaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_annei_hatoma, this);
    }
}
