
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.Loading;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusAsync;
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.UrlSelector;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.FragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.QueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.nodes.Document;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity implements  QueryInterface, StatusAsync.AsyncTaskCallback {

    Liner mLiner;
    Fragment mFragment;
    Loading mLoading;
    /**
     * クエリ起動中かどうか
     */
    private boolean mQuerying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        mLiner = getIntent().getParcelableExtra(StatusDetailActivity.class.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        setTitleString();
        loadAd();
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

    private Loading getLoading() {
        if (mLoading == null) {
            mLoading = new Loading(this);
        }
        return mLoading;
    }

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
        mFragment = StatusDetailFragment.NewInstance(mLiner);
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
                if (!mQuerying) {
                    createDetail();
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

    /**
     * Fragmentの準備が完了
     */
    @Override
    public void startQuery() {
        createDetail();
    }

    /**
     * 詳細の作成開始
     */
    private void createDetail() {
        if (mFragment != null && mFragment instanceof FragmentInterface) {
            ((FragmentInterface) mFragment).onStartQuery();
        }
        mQuerying = true;
        // getLoaderManager().initLoader(1, null, this);
        String url = UrlSelector.getDetailUrl(getApplicationContext(), mLiner.company, mLiner.port);
        new StatusAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    @Override
    public void preExecute() {
        getLoading().show();
        mQuerying = true;
    }

    @Override
    public void postExecute(Document document) {

        String result;
        try {
            // 安栄のHTMLパース呼び出し
            result = AnneiDetailParser.pars(document);
            // 結果を通知
            if (mFragment != null && mFragment instanceof FragmentInterface) {
                ((FragmentInterface) mFragment).onResultQuery(mLiner, result);
            }
            // 終了
            if (mFragment != null && mFragment instanceof FragmentInterface) {
                ((FragmentInterface) mFragment).onFinishQuery();
            }
        } catch (Exception e) {
            Log.d("StatusDetailActivity", "e:" + e);
            Timber.d("エラー発生！！");
            Timber.d(e.getMessage());
            Timber.d(e.getLocalizedMessage());
            if (mFragment != null && mFragment instanceof FragmentInterface) {
                ((FragmentInterface) mFragment).onFailedQuery();
            }
        } finally {
            mQuerying = false;
            getLoading().close();
        }
    }
}
