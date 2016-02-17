
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表
 */
public class AnneiTimeTableView extends FrameLayout {

    @Bind(R.id.view_time_table_annei_taketomi)
    AnneiTimeTableTaketomiView mAnneiTimeTableTaketomiView;
    @Bind(R.id.view_time_table_annei_kohama)
    AnneiTimeTableKohamaView mAnneiTimeTableKohamaView;
    @Bind(R.id.view_time_table_annei_kurhoshima)
    AnneiTimeTableKuroshimaView mAnneiTimeTableKuroshimaView;
    @Bind(R.id.view_time_table_annei_oohara)
    AnneiTimeTableOoharaView mAnneiTimeTableOoharaView;
    @Bind(R.id.view_time_table_annei_uehara)
    AnneiTimeTableUeharaView mAnneiTimeTableUeharaView;
    @Bind(R.id.view_time_table_annei_hatoma)
    AnneiTimeTableHatomaView mAnneiTimeTableHatomaView;
    @Bind(R.id.view_time_table_annei_hateruma)
    AnneiTimeTableHaterumaView mAnneiTimeTableHaterumaView;

    @Bind({
            R.id.view_time_table_annei_taketomi,
            R.id.view_time_table_annei_kohama,
            R.id.view_time_table_annei_kurhoshima,
            R.id.view_time_table_annei_oohara,
            R.id.view_time_table_annei_uehara,
            R.id.view_time_table_annei_hatoma,
            R.id.view_time_table_annei_hateruma

    })
    List<View> aneiViews;

    public AnneiTimeTableView(Context context) {
        super(context);
    }

    public AnneiTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_annei, this);
        ButterKnife.bind(this, layout);
    }

    /***
     * 非表示一括切り替え用
     */
    protected static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(GONE);
        }
    };

    /**
     * 会社で表示を切り替え
     *
     * @param port 港
     */
    public void switchPortView(Port port) {
        if (port == null) {
            return;
        }
        ButterKnife.apply(aneiViews, DISABLE);
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
