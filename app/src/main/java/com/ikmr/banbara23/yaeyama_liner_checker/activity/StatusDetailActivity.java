
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.Loading;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusAsync;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusAsyncTaskLoader;
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.UrlSelector;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.FragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.QueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.nodes.Document;

import timber.log.Timber;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Document>, QueryInterface, StatusAsync.AsyncTaskCallback {

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

        setTitleString();
        loadAd();
        if (savedInstanceState != null) {
            mLiner = (Liner) savedInstanceState.get(Liner.class.getCanonicalName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLoading == null) {
            mLoading = new Loading(this);
        }
        if (mFragment == null) {
            createFragment();
        }
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
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        String url = UrlSelector.getDetailUrl(getApplicationContext(), mLiner.company, mLiner.port);
        StatusAsyncTaskLoader appLoader = new StatusAsyncTaskLoader(getApplication(), url);

        // loaderの開始
        appLoader.forceLoad();

        return appLoader;
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document document) {
        if (document == null) {
            // エラーを通知
            if (mFragment != null && mFragment instanceof FragmentInterface) {
                ((FragmentInterface) mFragment).onFailedQuery();
            }
            return;
        }
        String result = null;
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
        }
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

    /**
     * @param view 電話で問い合わせクリック
     */
    public void onTellClicked(View view) {
        Toast.makeText(getApplicationContext(), "電話で問い合わせクリック", Toast.LENGTH_SHORT);
    }

    /**
     * @param view サイトで確認クリック
     */
    public void onWebClicked(View view) {
        Toast.makeText(getApplicationContext(), "サイトで確認クリック", Toast.LENGTH_SHORT);
    }

    @Override
    public void preExecute() {
        mLoading.show();
        mQuerying = true;
    }

    @Override
    public void postExecute(Document document) {
        // if (document == null) {
        // // エラーを通知
        // if (mFragment != null && mFragment instanceof FragmentInterface) {
        // ((FragmentInterface) mFragment).onFailedQuery();
        // }
        // mLoading.close();
        // mQuerying = false;
        // return;
        // }
        String result = null;
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
            mLoading.close();
        }
    }
}
