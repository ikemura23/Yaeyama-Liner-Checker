
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableActivity;

public class TopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        loadAd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 広告読み込み
     */
    protected void loadAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        if (mAdView == null) {
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /**
     * 安栄クリック
     * 
     * @param view 安栄観光ボタン
     */
    public void anneiClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.ANNEI);
        // todo:shared elementsの途中
        // shared elements http://googledevjp.blogspot.jp/2014/11/android.html
        // String transitionName = getString(R.string.transition_top_annei);
        // ActivityOptionsCompat options =
        // ActivityOptionsCompat.makeSceneTransitionAnimation(this,)
        startActivity(intent);
    }

    /**
     * 八重山観光フェリークリック
     * 
     * @param view
     */
    public void ykfClick(View view) {
        Intent intent = new Intent(this, StatusListActivity.class);
        intent.putExtra(StatusListActivity.PARAM_COMPANY, Company.YKF);
        startActivity(intent);
    }

    /**
     * 時刻表クリック
     *
     * @param view
     */
    public void timeTableClick(View view) {
        Intent intent = new Intent(this, TimeTableActivity.class);
        startActivity(intent);
    }

    /**
     * 八重山観光フェリークリック
     *
     * @param view
     */
    public void otherClick(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }
}
