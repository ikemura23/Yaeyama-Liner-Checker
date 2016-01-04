
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.FragmentApiQueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailAnneiFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.util.StringUtils;

import butterknife.ButterKnife;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailAnneiActivity extends BaseActivity implements FragmentApiQueryInterface {

    Liner mLiner;
    Fragment mFragment;
    // Loading mLoading;
    /**
     * クエリ起動中かどうか
     */
    private boolean mQuerying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        mLiner = getIntent().getParcelableExtra(StatusDetailAnneiActivity.class.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        setTitleString();
        if (savedInstanceState != null) {
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

    // private Loading getLoading() {
    // if (mLoading == null) {
    // mLoading = new Loading(this);
    // }
    // return mLoading;
    // }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Liner.class.getCanonicalName(), mLiner);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
    }

    /**
     * フラグメント作成
     */
    private void createFragment() {
        mFragment = StatusDetailAnneiFragment.NewInstance(mLiner);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
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
                // mQuerying=trueならFragmentが通信中
                if (!mQuerying) {
                    createFragment();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
     * Fragmentの準備が完了
     */
    // @Override
    public void startQuery() {
        mQuerying = true;
    }

    @Override
    public void finishQuery() {
        mQuerying = false;
    }
}
