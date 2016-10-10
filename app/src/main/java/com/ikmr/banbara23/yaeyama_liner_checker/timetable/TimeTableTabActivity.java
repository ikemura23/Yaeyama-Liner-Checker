
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.AnalyticsUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 時刻表タブ画面Activity
 */
public class TimeTableTabActivity extends BaseActivity {

    private static final String TAG = Const.FireBaseAnalitycsTag.TIME_TABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_tab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        int currentPosition = TimeTablePositionHelper.getInitTabPosition();
        createTab(currentPosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AnalyticsUtils.logAppOpenEvent(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_timetable_tab_layout);
        if (tabLayout == null)
            return;
        TimeTablePositionHelper.setCurrentTabPosition(tabLayout.getSelectedTabPosition());
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

    /**
     * タブの作成
     *
     * @param currentPosition 選択したタブ位置
     */
    private void createTab(int currentPosition) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_timetable_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_tab_name_annei_and_ykf)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.company_tab_name_dream)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TimeTablePagerAdapter adapter = new TimeTablePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                AnalyticsUtils.logSelectEvent(TAG, "tab_select_" + tab.getPosition());
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
