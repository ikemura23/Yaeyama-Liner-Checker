
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusDetailFragment;

/**
 * ステータス詳細のActivity
 */
public class StatusDetailActivity extends BaseActivity {
    final static String PARAM = "port";

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);
        value = getIntent().getStringExtra(PARAM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            StatusDetailFragment statusDetailFragment = StatusDetailFragment.NewInstance(value);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, statusDetailFragment)
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
}