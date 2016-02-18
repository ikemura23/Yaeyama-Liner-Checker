
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei.AnneiTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream.DreamTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表フラグメント
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.fragment_timetable_tab_spinner)
    Spinner mSpinner;

    @Bind(R.id.fragment_timetable_annei_view)
    AnneiTimeTableView mAnneiTimeTableView;

    @Bind(R.id.fragment_timetable_ykf_view)
    YkfTimeTableView mYkfTimeTableView;

    @Bind(R.id.fragment_timetable_dream_view)
    DreamTimeTableView mDreamTimeTableView;

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
        setCommpanyToView();
        createSpinner();

        return view;
    }

    private void setCommpanyToView() {
        Company company = (Company) getArguments().getSerializable(TimeTableFragment.class.getCanonicalName());
        if (company == null) {
            return;
        }
        Log.d("TimeTableFragment", "company:" + company);
        switch (company) {
            case ANNEI:
                mAnneiTimeTableView.setVisibility(View.VISIBLE);
                break;
            case YKF:
                mYkfTimeTableView.setVisibility(View.VISIBLE);
                break;
            case DREAM:
                mDreamTimeTableView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void createSpinner() {
        mSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getPortList());
        // adapter.addAll(getPortList());
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        switch (company) {
            case ANNEI:
                mAnneiTimeTableView.switchPortView(getAnneiPort(position));
                break;
            case YKF:
                mYkfTimeTableView.switchPortView(getYkfPort(position));
                break;
            case DREAM:
                mDreamTimeTableView.switchPortView(getDreamPort(position));
                break;
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
                return Port.HATERUMA;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
