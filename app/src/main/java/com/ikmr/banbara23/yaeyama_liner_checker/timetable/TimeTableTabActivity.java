
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ikmr.banbara23.yaeyama_liner_checker.PagerAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.BaseActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表タブ画面Activity
 */
public class TimeTableTabActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.activity_timetable_tab_spinner)
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_tab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mSpinner.setOnItemSelectedListener(this);

        // Company company = (Company)
        // getIntent().getSerializableExtra(StatusListTabActivity.class.getCanonicalName());
        Company company = Company.ANNEI;
        int currentPosition = 0;
        // switch (company) {
        // case ANNEI:
        // currentPosition = TAB_FIRST;
        // break;
        // case YKF:
        // currentPosition = TAB_SECOND;
        // break;
        // case DREAM:
        // currentPosition = TAB_THREAD;
        // break;
        // default:
        // currentPosition = 0;
        // }

        createTab(currentPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * タブの作成
     *
     * @param currentPosition 選択したタブ位置
     */
    private void createTab(int currentPosition) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_timetable_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_name_annei)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_name_ykf)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_name_dream)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(currentPosition);
    }
}
