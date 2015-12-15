
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class TimeTableView extends LinearLayout {

    HashMap<Port, View> timeViews = new HashMap<>();

    @Bind(R.id.view_time_table_taketomi)
    TimeTableTaketomiView mViewTimeTableTaketomi;
    @Bind(R.id.view_time_table_uehara)
    TimeTableUeharaView mViewTimeTableUehara;
    @Bind(R.id.view_time_table_oohara)
    TimeTableOoharaView mViewTimeTableOohara;
    @Bind(R.id.view_time_table_kohama)
    TimeTableKohamaView mViewTimeTableKohama;
    @Bind(R.id.view_time_table_kurhoshima)
    TimeTableKuroshimaView mViewTimeTableKurhoshima;
    @Bind(R.id.view_time_table_hateruma)
    TimeTableHaterumaView mViewTimeTableHateruma;
    @Bind(R.id.view_time_table_hatoma)
    TimeTableHatomaView mViewTimeTableHatoma;

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table, this);
        ButterKnife.bind(this, layout);

        timeViews.put(Port.HATERUMA, mViewTimeTableHateruma);
        timeViews.put(Port.HATOMA, mViewTimeTableHatoma);
        timeViews.put(Port.KOHAMA, mViewTimeTableKohama);
        timeViews.put(Port.KUROSHIMA, mViewTimeTableKurhoshima);
        timeViews.put(Port.OOHARA, mViewTimeTableOohara);
        timeViews.put(Port.TAKETOMI, mViewTimeTableTaketomi);
        timeViews.put(Port.UEHARA, mViewTimeTableUehara);
    }

    /**
     * 会社で表示を切り替え
     * 
     * @param company
     * @param port
     */
    public void switchView(Company company, Port port) {
        if (company == null) {
            return;
        }
        if (port == null) {
            return;
        }
        if (timeViews == null || timeViews.isEmpty()) {
            return;
        }

        for (Map.Entry<Port, View> timeView : timeViews.entrySet()) {
            // 港が一致したら観光会社の時刻表を表示、違えば時刻表ごと非表示
            if (timeView.getKey() == port) {
                TimeTableBaseView timeTableView = (TimeTableBaseView) timeView.getValue();
                timeTableView.setVisibility(VISIBLE);
                timeTableView.switchViews(company);
            }
            else {
                timeView.getValue().setVisibility(GONE);
            }
        }
    }
}
