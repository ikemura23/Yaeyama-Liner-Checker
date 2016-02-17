
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

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
 * ドリーム観光 時刻表 rootView
 */
public class DreamTimeTableView extends FrameLayout {

    @Bind(R.id.view_dream_time_table_taketomi)
    DreamTimeTableTaketomiView mViewDreamTimeTableTaketomi;
    @Bind(R.id.view_dream_time_table_kohama)
    DreamTimeTableKohamaView mViewDreamTimeTableKohama;
    @Bind(R.id.view_dream_time_table_oohara)
    DreamTimeTableOoharaView mViewDreamTimeTableOohara;
    @Bind(R.id.view_dream_time_table_kurhoshima)
    DreamTimeTableKuroshimaView mViewDreamTimeTableKurhoshima;
    @Bind(R.id.view_dream_time_table_uehara)
    DreamTimeTableUeharaView mViewDreamTimeTableUehara;

    @Bind({
            R.id.view_dream_time_table_taketomi,
            R.id.view_dream_time_table_kohama,
            R.id.view_dream_time_table_kurhoshima,
            R.id.view_dream_time_table_oohara,
            R.id.view_dream_time_table_uehara
    })
    List<View> mViews;
    /***
     * 非表示一括切り替え用
     */
    protected static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(GONE);
        }
    };

    public DreamTimeTableView(Context context) {
        super(context);
    }

    public DreamTimeTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View layout = LayoutInflater.from(context).inflate(R.layout.view_time_table_dream, this);
        ButterKnife.bind(this, layout);
    }

    /**
     * 港で表示を切り替え
     *
     * @param port
     * @param port
     */
    public void switchPortView(Port port) {
        if (port == null) {
            return;
        }
        ButterKnife.apply(mViews, DISABLE);
        switch (port) {
            case TAKETOMI:
                mViewDreamTimeTableTaketomi.setVisibility(VISIBLE);
                break;
            case KOHAMA:
                mViewDreamTimeTableKohama.setVisibility(VISIBLE);
                break;
            case OOHARA:
                mViewDreamTimeTableOohara.setVisibility(VISIBLE);
                break;
            case KUROSHIMA:
                mViewDreamTimeTableKurhoshima.setVisibility(VISIBLE);
                break;
            case HATOMA_UEHARA:
                mViewDreamTimeTableUehara.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }
}
