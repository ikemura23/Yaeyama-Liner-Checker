
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf;

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
public class YkfTimeTableView extends FrameLayout {

    @Bind(R.id.view_time_table_ykf_taketomi)
    YkfTimeTableTaketomiView mYkfTimeTableTaketomiView;
    @Bind(R.id.view_time_table_ykf_kohama)
    YkfTimeTableKohamaView mYkfTimeTableKohamaView;
    @Bind(R.id.view_time_table_ykf_kurhoshima)
    YkfTimeTableKuroshimaView mYkfTimeTableKuroshimaView;
    @Bind(R.id.view_time_table_ykf_oohara)
    YkfTimeTableOoharaView mYkfTimeTableOoharaView;
    @Bind(R.id.view_time_table_ykf_uehara)
    YkfTimeTableUeharaView mYkfTimeTableUeharaView;
    @Bind(R.id.view_time_table_ykf_hatoma)
    YkfTimeTableHatomaView mYkfTimeTableHatomaView;

    @Bind({
            R.id.view_time_table_ykf_taketomi,
            R.id.view_time_table_ykf_kurhoshima,
            R.id.view_time_table_ykf_kohama,
            R.id.view_time_table_ykf_oohara,
            R.id.view_time_table_ykf_uehara,
            R.id.view_time_table_ykf_hatoma
    })
    List<View> mViews;

    protected static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(GONE);
        }
    };

    public YkfTimeTableView(Context context) {
        super(context);
    }

    public YkfTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_ykf, this);
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
        ButterKnife.apply(mViews, DISABLE);
        switch (port) {
            case TAKETOMI:
                mYkfTimeTableTaketomiView.setVisibility(VISIBLE);
                break;
            case KOHAMA:
                mYkfTimeTableKohamaView.setVisibility(VISIBLE);
                break;
            case OOHARA:
                mYkfTimeTableOoharaView.setVisibility(VISIBLE);
                break;
            case KUROSHIMA:
                mYkfTimeTableKuroshimaView.setVisibility(VISIBLE);
                break;
            case UEHARA:
                mYkfTimeTableUeharaView.setVisibility(VISIBLE);
                break;
            case HATOMA:
                mYkfTimeTableHatomaView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }
}
