
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class AnneiTimeTableView extends FrameLayout {

    @Bind(R.id.view_timetable_header_annei_ritou)
    TextView mViewTimetableHeaderAnneiRitou;
    @Bind(R.id.view_timetable_root_annei)
    LinearLayout mViewTimetableRootAnnei;

    @Bind(R.id.view_time_table_annei_taketomi)
    AnneiTimeTableTaketomiView mAnneiTimeTableTaketomiView;
    @Bind(R.id.view_time_table_annei_uehara)
    AnneiTimeTableUeharaView mAnneiTimeTableUeharaView;
    @Bind(R.id.view_time_table_annei_oohara)
    AnneiTimeTableOoharaView mAnneiTimeTableOoharaView;
    @Bind(R.id.view_time_table_annei_kohama)
    AnneiTimeTableKohamaView mAnneiTimeTableKohamaView;
    @Bind(R.id.view_time_table_annei_kurhoshima)
    AnneiTimeTableKuroshimaView mAnneiTimeTableKuroshimaView;
    @Bind(R.id.view_time_table_annei_hatoma)
    AnneiTimeTableHatomaView mAnneiTimeTableHatomaView;
    @Bind(R.id.view_time_table_annei_hateruma)
    AnneiTimeTableHaterumaView mAnneiTimeTableHaterumaView;

    public AnneiTimeTableView(Context context) {
        super(context);
    }

    public AnneiTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_annei, this);
        ButterKnife.bind(this, layout);

    }

    /**
     * 会社で表示を切り替え
     * 
     * @param port 港
     */
    public void switchPortView(Port port) {
        if (port == null) {
            return;
        }
        mViewTimetableRootAnnei.setVisibility(VISIBLE);
        mViewTimetableHeaderAnneiRitou.setText(port.getPortSimple());

        switch (port) {
            case TAKETOMI:
                mAnneiTimeTableTaketomiView.setVisibility(VISIBLE);
                break;
            case KOHAMA:
                mAnneiTimeTableKohamaView.setVisibility(VISIBLE);
                break;
            case OOHARA:
                mAnneiTimeTableOoharaView.setVisibility(VISIBLE);
                break;
            case KUROSHIMA:
                mAnneiTimeTableKuroshimaView.setVisibility(VISIBLE);
                break;
            case UEHARA:
                mAnneiTimeTableUeharaView.setVisibility(VISIBLE);
                break;
            case HATOMA:
                mAnneiTimeTableHatomaView.setVisibility(VISIBLE);
                break;
            case HATERUMA:
                mAnneiTimeTableHaterumaView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }
}
