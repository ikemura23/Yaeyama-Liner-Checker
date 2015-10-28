
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
import com.ikmr.banbara23.yaeyama_liner_checker.StatusAsync;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.ListFragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.QueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiListParser;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.YkfParser;

import org.jsoup.nodes.Document;

import timber.log.Timber;

/**
 * ステータス一覧Activity
 */
public class StatusListActivity extends BaseActivity implements
        StatusListAdapter.ListItemClickListener, QueryInterface, StatusAsync.AsyncTaskCallback {

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
        if (savedInstanceState == null) {
            mFragment = StatusListFragment.NewInstance(mCompany);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
        mLoading = new Loading(this);
    }

    /**
     * タイトルの設定
     */
    private void setPageTitle() {
        if (mCompany == null) {
            return;
        }
        // String title = mCompany.getCompanyName() +
        // getString(R.string.title_activity_status_list);
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
    public void startQuery() {
        // 一覧の取得開始
        createList();
    }

    /**
     * 一覧の取得開始
     */
    private void createList() {
        // Toast.makeText(this, "createList", Toast.LENGTH_SHORT).show();

        String url;
        if (mCompany == Company.ANNEI) {
            url = getApplicationContext().getString(R.string.url_annei_list);
        } else {
            url = getApplicationContext().getString(R.string.url_ykf_list);
        }

        new StatusAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    /**
     * リストのセルビュークリック処理
     *
     * @param liner
     */
    @Override
    public void onItemClick(Liner liner) {
        liner.setCompany(mCompany);

        if (mCompany == Company.ANNEI) {
            Intent intent = new Intent(this, StatusDetailActivity.class);
            intent.putExtra(StatusDetailActivity.class.getName(), liner);
            startActivity(intent);
        } else {
            YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
            ykfLinerDetail.setLiner(liner);
            ykfLinerDetail.setUpdateTime(mResult.getUpdateTime());
            ykfLinerDetail.setTitle(mResult.getTitle());

            Intent intent = new Intent(this, StatusDetailWebActivity.class);
            intent.putExtra(StatusDetailWebActivity.class.getName(), ykfLinerDetail);
            startActivity(intent);
        }

    }

    @Override
    public void preExecute() {
         if (mFragment != null && mFragment instanceof ListFragmentInterface)
         {
         ((ListFragmentInterface) mFragment).onStartQuery();
         }
        mLoading.show();
        mQuerying = true;
    }

    @Override
    public void postExecute(Document doc) {
        try {
            if (mCompany == Company.ANNEI) {
                // 安栄のHTMLパース呼び出し
                mResult = AnneiListParser.pars(doc);
            } else {
                // 八重山観光フェリーのHTMLパース呼び出し
                mResult = YkfParser.pars(doc);
            }
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
            mLoading.close();
            mQuerying = false;
        }
    }
}
