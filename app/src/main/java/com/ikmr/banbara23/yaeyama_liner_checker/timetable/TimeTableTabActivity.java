
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表タブ画面Activity
 */
public class TimeTableTabActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.activity_timetable_spinner)
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
