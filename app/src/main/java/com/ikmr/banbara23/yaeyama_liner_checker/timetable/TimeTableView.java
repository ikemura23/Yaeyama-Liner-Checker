
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class TimeTableView extends LinearLayout {

    ArrayMap<Port, View> timeViews = new ArrayMap<>();
    @Bind(R.id.view_time_table_kohama)
    TimeTableKohamaView mTimeTableKohamaView;

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table, this);
        ButterKnife.bind(this);

        timeViews.put(Port.HATERUMA, layout.findViewById(R.id.view_time_table_hateruma));
        timeViews.put(Port.HATOMA, layout.findViewById(R.id.view_time_table_hatoma));
        timeViews.put(Port.KOHAMA, layout.findViewById(R.id.view_time_table_kohama));
        timeViews.put(Port.KUROSHIMA, layout.findViewById(R.id.view_time_table_kurhoshima));
        timeViews.put(Port.OOHARA, layout.findViewById(R.id.view_time_table_oohara));
        timeViews.put(Port.TAKETOMI, layout.findViewById(R.id.view_time_table_taketomi));
        timeViews.put(Port.UEHARA, layout.findViewById(R.id.view_time_table_uehara));
    }

    /**
     * 会社で表示を切り替え
     * 
     * @param company
     * @param port
     */
    public void switchView(Company company, Port port) {
        if (mTimeTableKohamaView != null) {
            mTimeTableKohamaView.switchViews(company);
        }
        for (int i = 0; i < timeViews.size(); i++) {
            if (timeViews.keyAt(i) == port) {
                timeViews.get(port).setVisibility(VISIBLE);
                ((TimeTableBaseView) timeViews.get(port)).switchViews(company);
            }
            else {
                timeViews.get(port).setVisibility(GONE);
            }
        }
    }
}
