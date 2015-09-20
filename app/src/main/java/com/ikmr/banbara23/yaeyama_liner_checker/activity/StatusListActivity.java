
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.ikmr.banbara23.yaeyama_liner_checker.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * ステータス一覧Activity
 */
public class StatusListActivity extends BaseActivity implements
        StatusListAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Document> {

    final static String PARAM_COMPANY = "company";
    Company mCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompany = (Company) getIntent().getSerializableExtra(PARAM_COMPANY);
        setPageTitle();

        if (savedInstanceState == null) {
            StatusListFragment statusListFragment = StatusListFragment.NewInstance(mCompany);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, statusListFragment)
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
        return appLoader;
    }

    @Override
    public void onLoadFinished(Loader<Document> loader, Document doc) {
        if (doc != null) {
            // 取得成功
            Element content = (Element) doc.getElementById("mp-tfa");
            Elements images = content.getElementsByTag("img");
            Element image = images.get(0);
            String imagePath = "http:" + image.attr("src");

            AQuery imageView = new AQuery(this);
            // imageView.id(R.id.imageView).visible().webImage(imagePath, true,
            // false, 0);
            Log.d("imagePath", imagePath);
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
                // HTML取得
                doc = Jsoup.connect("http://en.wikipedia.org/wiki/Main_Page").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }
    }

}
