
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

/**
 * Created by banbara23 on 15/11/03.
 */
public class OtherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 八重山観光フェリークリック
     *
     * @param view
     */
    public void mailClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.YKF);
        startActivity(intent);
    }
}
