
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表フラグメント
 */
public class TimeTableFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.activity_timetable_tab_spinner)
    Spinner mSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        mSpinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TimeTableFragment", "position:" + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static TimeTableFragment NewInstance(Company company) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TimeTableFragment.class.getCanonicalName(), company);
        fragment.setArguments(bundle);
        return fragment;
    }
}
