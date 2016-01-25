
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.PagerAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

/**
 * 一覧タブActivity
 */
public class StatusListTabActivity extends BaseActivity {
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
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_list_tab_layout);
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
        int initCurrentItem = 0;
        switch (company) {
            case ANNEI:
                initCurrentItem = 0;
                break;
            case YKF:
                initCurrentItem = 1;
                break;
            case DREAM:
                initCurrentItem = 2;
                break;
        }
        viewPager.setCurrentItem(initCurrentItem);
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
                // if (!mQuerying) {
                // createList();
                // }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
