
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.PagerAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListTabFragment;

/**
 * 一覧タブActivity
 */
public class StatusListTabActivity extends BaseActivity {

    private static final int TAB_FIRST = 0;
    private static final int TAB_SECOND = 1;
    private static final int TAB_THREAD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tab);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Toolbar toolbar = (Toolbar)
        // findViewById(R.id.activity_list_tab_toolbar);
        // setSupportActionBar(toolbar);
        Company company = (Company) getIntent().getSerializableExtra(StatusListTabActivity.class.getCanonicalName());
        int currentPosition;
        switch (company) {
            case ANNEI:
                currentPosition = TAB_FIRST;
                break;
            case YKF:
                currentPosition = TAB_SECOND;
                break;
            case DREAM:
                currentPosition = TAB_THREAD;
                break;
            default:
                currentPosition = 0;
        }

        createTab(currentPosition);
    }

    /**
     * タブの作成
     * 
     * @param currentPosition 選択したタブ位置
     */
    private void createTab(int currentPosition) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_list_tab_layout);
        tabLayout.removeAllTabs();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_reload:
                updateCurrentFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 現在表示中のフラグメントを更新
     */
    private void updateCurrentFragment() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        StatusListTabFragment statusListTabFragment = (StatusListTabFragment) ((PagerAdapter) viewPager.getAdapter()).findFragmentByPosition(viewPager, viewPager.getCurrentItem());
        if (statusListTabFragment != null) {
            statusListTabFragment.resetTimeStamp();
            statusListTabFragment.onResume();
        }
    }
}
