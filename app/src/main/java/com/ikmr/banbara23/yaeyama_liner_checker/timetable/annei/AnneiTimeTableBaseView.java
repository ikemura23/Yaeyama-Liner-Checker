
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 時刻表の継承元View
 */
public abstract class AnneiTimeTableBaseView extends LinearLayout {
    public AnneiTimeTableBaseView(Context context) {
        super(context);
    }

    public AnneiTimeTableBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
