
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusAsyncTaskLoader;
import com.ikmr.banbara23.yaeyama_liner_checker.UrlSelector;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.FragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.QueryInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailFragment;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.nodes.Document;

import timber.log.Timber;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Document>, QueryInterface {

    Liner mLiner;
    // 観光会社
    private Company mCompany;
    Fragment mFragment;
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

        if (savedInstanceState == null) {
            mFragment = StatusDetailFragment.NewInstance(mLiner);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
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
                Toast.makeText(this, "更新処理", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        String url = UrlSelector.geDetailtUrl(getApplicationContext(), mCompany, mLiner.port);
        StatusAsyncTaskLoader appLoader = new StatusAsyncTaskLoader(getApplication(), url);

        // loaderの開始
        appLoader.forceLoad();

        return null;
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
        Result result = null;
        try {
            // 安栄のHTMLパース呼び出し
            result = AnneiDetailParser.pars(document);
            // 結果を通知
            if (mFragment != null && mFragment instanceof FragmentInterface) {
                ((FragmentInterface) mFragment).onResultQuery(result);
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
}
