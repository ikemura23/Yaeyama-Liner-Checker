
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表フラグメント
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.fragment_timetable_tab_spinner)
    Spinner mSpinner;

    // @Bind(R.id.fragment_timetable_annei_view)
    // AnneiTimeTableView mAnneiTimeTableView;

    // @Bind(R.id.fragment_timetable_ykf_view)
    // YkfTimeTableView mYkfTimeTableView;
    //
    // @Bind(R.id.fragment_timetable_dream_view)
    // DreamTimeTableView mDreamTimeTableView;

    @Bind(R.id.fragment_timetable_layout)
    LinearLayout mTimeTableLayout;

    @Bind(R.id.adView)
    AdView mAdView;

    View viewTaketomi;

    public static TimeTableFragment NewInstance(Company company) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TimeTableFragment.class.getCanonicalName(), company);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        // setCommpanyToView();
        createSpinner();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAdView == null) {
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    // private void setCommpanyToView() {
    // Company company = (Company)
    // getArguments().getSerializable(TimeTableFragment.class.getCanonicalName());
    // if (company == null) {
    // return;
    // }
    // switch (company) {
    // case ANNEI:
    // // mAnneiTimeTableView.setVisibility(View.VISIBLE);
    // break;
    // case YKF:
    // mYkfTimeTableView.setVisibility(View.VISIBLE);
    // break;
    // case DREAM:
    // mDreamTimeTableView.setVisibility(View.VISIBLE);
    // break;
    // }
    // }

    private void createSpinner() {
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                R.layout.spinner_item,
                getPortList());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    private String[] getPortList() {
        Company company = (Company) getArguments().getSerializable(TimeTableFragment.class.getCanonicalName());
        if (company == null) {
            return new String[0];
        }
        switch (company) {
            case ANNEI:
                return getResources().getStringArray(R.array.annei_port_list);
            case YKF:
                return getResources().getStringArray(R.array.ykf_port_list);
            case DREAM:
                return getResources().getStringArray(R.array.dream_port_list);
        }
        return new String[0];
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Company company = (Company) getArguments().getSerializable(TimeTableFragment.class.getCanonicalName());
        if (company == null) {
            return;
        }
        mTimeTableLayout.removeAllViews();
        int viewResourceId = 0;
        switch (company) {
            case ANNEI:
                viewResourceId = getAnneiTimeTableLayoutResourceId(position);
                break;
            case YKF:
                viewResourceId = getYkfTimeTableLayoutResourceId(position);
                // mYkfTimeTableView.switchPortView(getYkfPort(position));
                break;
            case DREAM:
                viewResourceId = getDreamTimeTableLayoutResourceId(position);
                // mDreamTimeTableView.switchPortView(getDreamPort(position));
                break;
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewTaketomi = inflater.inflate(viewResourceId, null);
        mTimeTableLayout.addView(viewTaketomi);
    }

    private int getAnneiTimeTableLayoutResourceId(int position) {
        switch (position) {
            case 0:
                return R.layout.view_time_table_annei_taketomi;
            case 1:
                return R.layout.view_time_table_annei_kohama;
            case 2:
                return R.layout.view_time_table_annei_kuroshima;
            case 3:
                return R.layout.view_time_table_annei_oohara;
            case 4:
                return R.layout.view_time_table_annei_uehara;
            case 5:
                return R.layout.view_time_table_annei_hatoma;
            case 6:
                return R.layout.view_time_table_annei_hateruma;
            default:
                return R.layout.view_time_table_annei_taketomi;
        }
    }

    private int getYkfTimeTableLayoutResourceId(int position) {
        switch (position) {
            case 0:
                return R.layout.view_time_table_ykf_taketomi;
            case 1:
                return R.layout.view_time_table_ykf_kohama;
            case 2:
                return R.layout.view_time_table_ykf_kuroshima;
            case 3:
                return R.layout.view_time_table_ykf_oohara;
            case 4:
                return R.layout.view_time_table_ykf_uehara;
            case 5:
                return R.layout.view_time_table_ykf_hatoma;
            default:
                return R.layout.view_time_table_ykf_taketomi;
        }
    }

    private int getDreamTimeTableLayoutResourceId(int position) {
        switch (position) {
            case 0:
                return R.layout.view_time_table_dream_taketomi;
            case 1:
                return R.layout.view_time_table_dream_kohama;
            case 2:
                return R.layout.view_time_table_dream_kuroshima;
            case 3:
                return R.layout.view_time_table_dream_oohara;
            case 4:
                return R.layout.view_time_table_dream_uehara;
            default:
                return R.layout.view_time_table_ykf_taketomi;
        }
    }

    private Port getAnneiPort(int position) {
        switch (position) {
            case 0:
                return Port.TAKETOMI;
            case 1:
                return Port.KOHAMA;
            case 2:
                return Port.KUROSHIMA;
            case 3:
                return Port.OOHARA;
            case 4:
                return Port.UEHARA;
            case 5:
                return Port.HATOMA;
            case 6:
                return Port.HATERUMA;
            default:
                return Port.TAKETOMI;
        }
    }

    private Port getYkfPort(int position) {
        switch (position) {
            case 0:
                return Port.TAKETOMI;
            case 1:
                return Port.KOHAMA;
            case 2:
                return Port.KUROSHIMA;
            case 3:
                return Port.OOHARA;
            case 4:
                return Port.UEHARA;
            case 5:
                return Port.HATOMA;
            default:
                return Port.TAKETOMI;

        }
    }

    private Port getDreamPort(int position) {
        switch (position) {
            case 0:
                return Port.TAKETOMI;
            case 1:
                return Port.KOHAMA;
            case 2:
                return Port.KUROSHIMA;
            case 3:
                return Port.OOHARA;
            case 4:
                return Port.HATOMA_UEHARA;
            default:
                return Port.TAKETOMI;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
