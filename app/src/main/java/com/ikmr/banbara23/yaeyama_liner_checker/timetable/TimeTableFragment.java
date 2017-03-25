
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
import com.ikmr.banbara23.yaeyama_liner_checker.AnalyticsUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表フラグメント
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = Const.FireBaseAnalitycsTag.TIME_TABLE;

    @Bind(R.id.fragment_timetable_tab_spinner)
    Spinner mSpinner;

    @Bind(R.id.fragment_timetable_layout)
    LinearLayout mTimeTableLayout;

    @Bind(R.id.adView)
    AdView mAdView;

    /**
     * NewInstance
     *
     * @param company 会社
     * @return TimeTableFragment
     */
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
        final AdRequest adRequest = new AdRequest.Builder().build();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdView.loadAd(adRequest);
            }
        });
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

    private void saveSpinnerPosition() {
        if (mSpinner != null) {
            TimeTablePositionHelper.setCurrentSpinnerPosition(getParamCompany(), mSpinner.getSelectedItemPosition());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSpinnerPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    /**
     * スピナー作成
     */
    private void createSpinner() {
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                R.layout.spinner_item,
                getPortList());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        Company company = getParamCompany();
        mSpinner.setSelection(TimeTablePositionHelper.getInitSpinnerPosition(company));
    }

    private Company getParamCompany() {
        return (Company) getArguments().getSerializable(TimeTableFragment.class.getCanonicalName());
    }

    /**
     * スピナーの表示リスト作成
     *
     * @return 港の配列
     */
    private String[] getPortList() {
        Company company = getParamCompany();
        if (company == null) {
            return new String[0];
        }
        switch (company) {
            case ANNEI:
                return getResources().getStringArray(R.array.annei_port_list);
            case DREAM:
                return getResources().getStringArray(R.array.dream_port_list);
        }
        return new String[0];
    }

    /**
     * スピナー選択
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
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
            case DREAM:
                viewResourceId = getDreamTimeTableLayoutResourceId(position);
                break;
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(viewResourceId, mTimeTableLayout);
        AnalyticsUtils.logSelectEvent(TAG, "port_select_" + company.getCompanyName());
    }

    /**
     * 表示するViewのResourceを返す
     *
     * @param position スピナー選択値
     * @return layoutのresource
     */
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

    /**
     * 表示するViewのResourceを返す
     *
     * @param position スピナー選択値
     * @return layoutのresource
     */
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
