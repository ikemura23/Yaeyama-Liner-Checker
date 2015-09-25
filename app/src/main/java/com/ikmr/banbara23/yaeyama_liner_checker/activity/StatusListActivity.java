
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.AnneiParser;
import com.ikmr.banbara23.yaeyama_liner_checker.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.ListFragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListFragment;

/**
 * ステータス一覧Activity
 */
public class StatusListActivity extends BaseActivity implements
        StatusListAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Document> {

    final static String PARAM_COMPANY = "company";
    // 観光会社
    private Company mCompany;
    // 通知用インターフェース
    private ListFragmentInterface mListFragmentInterface;
    /** クエリ起動中かどうか */
    private boolean mQuerying;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompany = (Company) getIntent().getSerializableExtra(PARAM_COMPANY);
        setPageTitle();

        if (savedInstanceState == null) {
            mFragment = StatusListFragment.NewInstance(mCompany);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }
    }

    private void setPageTitle() {
        if (mCompany == null) {
            return;
        }
        String title = mCompany.getCompanyName() + getString(R.string.title_activity_status_list);
        setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().initLoader(0, null, this);
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

    @Override
    public void onItemClick(String string) {
        Intent intent = new Intent(this, StatusDetailActivity.class);
        intent.putExtra(StatusDetailActivity.PARAM, string);
        startActivity(intent);
    }

    @Override
    public Loader<Document> onCreateLoader(int id, Bundle args) {
        MyAsyncTaskLoader appLoader = new MyAsyncTaskLoader(getApplication());

        // loaderの開始
        appLoader.forceLoad();
        //
        if (mFragment != null && mFragment instanceof ListFragmentInterface) {
            ((ListFragmentInterface) mFragment).onStartQuery();
        }
        return appLoader;
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document doc) {
        if (doc == null) {
            // エラーを通知
            if (mFragment != null && mFragment instanceof ListFragmentInterface) {
                ((ListFragmentInterface) mFragment).onFailedQuery();
            }
            return;
        }
        Result result = null;
        if (mCompany == Company.ANNEI) {
            // 安栄のHTMLパース呼び出し
            result = AnneiParser.pars(doc);
        } else {
            parsYkf(doc);
            // 八重山観光フェリーのHTMLパース呼び出し
        }
        // 結果を通知
        if (mFragment != null && mFragment instanceof ListFragmentInterface) {
            ((ListFragmentInterface) mFragment).onResultQuery(result);
        }
        // 終了
        if (mFragment != null && mFragment instanceof ListFragmentInterface) {
            ((ListFragmentInterface) mFragment).onFinishQuery();
        }
    }

    @Override
    public void onLoaderReset(Loader<Document> loader) {

    }

    public static class MyAsyncTaskLoader extends AsyncTaskLoader<Document> {

        public MyAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        public Document loadInBackground() {
            Document doc = null;
            try {
                String url = getContext().getString(R.string.url_annei_list);
                // HTML取得
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }
    }

    /**
     * 安栄のHTMLパース
     * 
     * @param doc HTMLデータ
     */
    private void parsAnnei(Document doc) {
        // AnneiParser.pars(doc);
    }

    /**
     * 八重山観光フェリーのHTMLパース
     * 
     * @param doc
     */
    private Result parsYkf(Document doc) {
        return null;
    }
}
