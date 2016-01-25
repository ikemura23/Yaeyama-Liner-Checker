
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.FragmentApiQueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailYkfFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.util.StringUtils;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailYkfActivity extends BaseActivity implements FragmentApiQueryInterface {

    YkfLinerDetail mYkfLinerDetail;
    Liner mLiner;

    Fragment mFragment;

    /**
     * クエリ起動中かどうか
     */
    private boolean mQuerying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        mYkfLinerDetail = getIntent().getParcelableExtra(StatusDetailYkfActivity.class.getName());
        mLiner = mYkfLinerDetail.getLiner();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // タイトル
        setTitleString();

        // 広告
        loadAd();

        if (savedInstanceState != null) {
            mYkfLinerDetail = (YkfLinerDetail) savedInstanceState.get(YkfLinerDetail.class.getCanonicalName());
            mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment == null) {
            createFragment();
        }
    }

    /**
     * フラグメント作成
     */
    private void createFragment() {
        mFragment = StatusDetailYkfFragment.NewInstance(mYkfLinerDetail);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(YkfLinerDetail.class.getCanonicalName(), mYkfLinerDetail);
        outState.putParcelable(Liner.class.getCanonicalName(), mLiner);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mYkfLinerDetail = (YkfLinerDetail) savedInstanceState.get(YkfLinerDetail.class.getCanonicalName());
        mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
    }

    /**
     * タイトルバーの設定
     */
    private void setTitleString() {
        if (mLiner == null) {
            return;
        }
        if (mLiner.getPort() == null) {
            return;
        }
        if (StringUtils.isEmpty(mLiner.getPort().getPort())) {
            setTitle("運航状況の詳細");
        }

        setTitle(mLiner.getPort().getPort() + "航路");
    }

    /**
     * 広告読み込み
     */
    protected void loadAd() {
        AdView adView = (AdView) findViewById(R.id.adView);
        if (adView == null) {
            return;
        }
        try {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } catch (Exception e) {
        }
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
                if (!mQuerying) {
                    createFragment();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startQuery() {
        mQuerying = true;
    }

    @Override
    public void finishQuery() {
        mQuerying = false;
    }
}
