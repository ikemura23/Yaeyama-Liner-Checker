
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 時刻表の継承元View
 */
public abstract class YkfTimeTableBaseView extends LinearLayout {
    public YkfTimeTableBaseView(Context context) {
        super(context);
    }

    public YkfTimeTableBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
