
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * トップActivity
 */
public class TopActivity extends BaseActivity {

    @OnClick(R.id.top_activity_annei)
    void anneiClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.ANNEI);
        startActivity(intent);
    }

    @OnClick(R.id.top_activity_ykf)
    void ykfClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.YKF);
        startActivity(intent);
    }

    @OnClick(R.id.top_activity_dream)
    void dreamClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.DREAM);
        startActivity(intent);
    }

    @OnClick(R.id.top_activity_timetable)
    void timeTableClick(View view) {
        Intent intent = new Intent(this, TimeTableActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.top_activity_app)
    void otherClick(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
