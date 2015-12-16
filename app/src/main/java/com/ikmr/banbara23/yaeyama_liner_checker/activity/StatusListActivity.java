
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.Loading;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAsync;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.ListFragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.QueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListFragment;

import timber.log.Timber;

/**
 * ステータス一覧Activity
 */
public class StatusListActivity extends BaseActivity implements
        StatusListAdapter.ListItemClickListener, QueryInterface, StatusListAsync.StatusListAsyncCallback {

    final static String PARAM_COMPANY = "company";
    // 観光会社
    private Company mCompany;
    /**
     * クエリ起動中かどうか
     */
    private boolean mQuerying;
    private Fragment mFragment;
    private Result mResult;

    Loading mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompany = (Company) getIntent().getSerializableExtra(PARAM_COMPANY);
        // タイトル
        setPageTitle();
        // 広告
        loadAd();
        // フラグメント
        if (savedInstanceState != null) {
            mCompany = (Company) savedInstanceState.get(PARAM_COMPANY);
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
        mFragment = StatusListFragment.NewInstance(mCompany);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    /**
     * タイトルの設定
     */
    private void setPageTitle() {
        if (mCompany == null) {
            return;
        }
        String title = mCompany.getCompanyName();
        setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_reload:
                if (!mQuerying) {
                    createList();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PARAM_COMPANY, mCompany);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCompany = (Company) savedInstanceState.get(PARAM_COMPANY);
    }

    @Override
    public void startQuery() {
        // 一覧の取得開始
        createList();
    }

    /**
     * 一覧の取得開始
     */
    private void createList() {
        String url;
        switch (mCompany) {
            case ANNEI:
                url = getApplicationContext().getString(R.string.url_annei_list);
                break;
            case YKF:
                url = getApplicationContext().getString(R.string.url_ykf_list);
                break;
            case DREAM:
                url = getApplicationContext().getString(R.string.url_dream_list);
                break;
            default:
                return;
        }
        new StatusListAsync(this, mCompany).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    /**
     * リストのセルビュークリック処理
     *
     * @param liner
     */
    @Override
    public void onItemClick(Liner liner) {
        liner.setCompany(mCompany);

        switch (mCompany) {
            case ANNEI:
                startStatusDetailActivity(liner);
                break;
            case YKF:
                startStatusDetailYkfActivity(liner);
                break;
            case DREAM:
                startStatusDetailDreamActivity(liner);
                break;
            default:
                break;
        }
    }

    /**
     * 安栄の詳細画面に遷移
     * 
     * @param liner 運航状況
     */
    private void startStatusDetailActivity(Liner liner) {
        Intent intent = new Intent(this, StatusDetailActivity.class);
        intent.putExtra(StatusDetailActivity.class.getName(), liner);
        startActivity(intent);
    }

    /**
     * 八重山観光フェリーの詳細に遷移
     * 
     * @param liner
     */
    private void startStatusDetailYkfActivity(Liner liner) {
        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
        ykfLinerDetail.setLiner(liner);
        ykfLinerDetail.setUpdateTime(mResult.getUpdateTime());
        ykfLinerDetail.setTitle(mResult.getTitle());

        Intent intent = new Intent(this, StatusDetailYkfActivity.class);
        intent.putExtra(StatusDetailYkfActivity.class.getName(), ykfLinerDetail);
        startActivity(intent);
    }

    /**
     * ドリーム観光の詳細に遷移
     * 
     * @param liner
     */
    private void startStatusDetailDreamActivity(Liner liner) {
        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
        ykfLinerDetail.setLiner(liner);
        ykfLinerDetail.setUpdateTime(mResult.getUpdateTime());
        ykfLinerDetail.setTitle(mResult.getTitle());

        Intent intent = new Intent(this, StatusDetailDreamActivity.class);
        intent.putExtra(StatusDetailDreamActivity.class.getName(), ykfLinerDetail);
        startActivity(intent);
    }

    public Loading getLoading() {
        if (mLoading == null) {
            mLoading = new Loading(this);
        }
        return mLoading;
    }

    @Override
    public void preExecute() {
        if (mFragment != null && mFragment instanceof ListFragmentInterface)
        {
            ((ListFragmentInterface) mFragment).onStartQuery();
        }
        getLoading().show();
        mQuerying = true;
    }

    /**
     * 後処理
     * 
     * @param result パース結果
     */
    @Override
    public void postExecute(Result result) {
        if (!mQuerying)
            return;

        mResult = result;
        try {
            // 結果を通知
            if (mFragment != null && mFragment instanceof ListFragmentInterface) {
                ((ListFragmentInterface) mFragment).onResultQuery(mResult);
            }
            // 終了
            if (mFragment != null && mFragment instanceof ListFragmentInterface) {
                ((ListFragmentInterface) mFragment).onFinishQuery();
            }
        } catch (Exception e) {
            Log.d("StatusListActivity", "e:" + e);
            Timber.d("エラー発生！！");
            Timber.d(e.getMessage());
            Timber.d(e.getLocalizedMessage());
            if (mFragment != null && mFragment instanceof ListFragmentInterface) {
                ((ListFragmentInterface) mFragment).onFailedQuery();
            }
        } finally {
            getLoading().close();
            mQuerying = false;
        }
    }
}
