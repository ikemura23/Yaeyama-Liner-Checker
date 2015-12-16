
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class DreamTimeTableView extends FrameLayout {

    // 時刻表の各港を格納する配列
    HashMap<Port, View> timeViews = new HashMap<>();

    // 時刻表ヘッダー
    @Bind(R.id.view_dream_timetable_header)
    LinearLayout mViewTimetableHeader;
    @Bind(R.id.view_dream_timetable_header_ishigaki)
    TextView mViewTimetableHeaderIshigaki;
    @Bind(R.id.view_dream_timetable_header_ritou)
    TextView mViewTimetableHeaderRitou;

    // 時刻表
    @Bind(R.id.view_dream_time_table_taketomi)
    DreamTimeTableTaketomiView mDreamTimeTableTaketomiView;

    // @Bind(R.id.view_time_table_uehara)
    // TimeTableUeharaView mViewTimeTableUehara;
    // @Bind(R.id.view_time_table_oohara)
    // TimeTableOoharaView mViewTimeTableOohara;
    // @Bind(R.id.view_time_table_kohama)
    // TimeTableKohamaView mViewTimeTableKohama;
    // @Bind(R.id.view_time_table_kurhoshima)
    // TimeTableKuroshimaView mViewTimeTableKurhoshima;
    // @Bind(R.id.view_time_table_hateruma)
    // TimeTableHaterumaView mViewTimeTableHateruma;
    // @Bind(R.id.view_time_table_hatoma)
    // TimeTableHatomaView mViewTimeTableHatoma;

    public DreamTimeTableView(Context context) {
        super(context);
    }

    public DreamTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_dream, this);
        ButterKnife.bind(this, layout);

        // timeViews.put(Port.HATERUMA, mViewTimeTableHateruma);
        // timeViews.put(Port.HATOMA, mViewTimeTableHatoma);
        // timeViews.put(Port.KOHAMA, mViewTimeTableKohama);
        // timeViews.put(Port.KUROSHIMA, mViewTimeTableKurhoshima);
        // timeViews.put(Port.OOHARA, mViewTimeTableOohara);
        // timeViews.put(Port.TAKETOMI, mViewTimeTableTaketomi);
        // timeViews.put(Port.UEHARA, mViewTimeTableUehara);
    }

    // /**
    // * 会社で表示を切り替え
    // *
    // * @param company
    // * @param port
    // */
    public void switchView(Port port) {
        if (port == null) {
            return;
        }
        if (timeViews == null || timeViews.isEmpty()) {
            return;
        }
        mViewTimetableHeader.setVisibility(VISIBLE);
        mViewTimetableHeaderRitou.setText(port.getPortSimple());

        mDreamTimeTableTaketomiView.setVisibility(VISIBLE);

        // for (Map.Entry<Port, View> timeView : timeViews.entrySet()) {
        // // 港が一致したら観光会社の時刻表を表示、違えば時刻表ごと非表示
        // if (timeView.getKey() == port) {
        // TimeTableBaseView timeTableView = (TimeTableBaseView)
        // timeView.getValue();
        // timeTableView.setVisibility(VISIBLE);
        // }
        // }
    }
}
