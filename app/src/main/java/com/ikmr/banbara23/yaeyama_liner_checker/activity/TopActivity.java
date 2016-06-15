
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.util.AnimationUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * トップActivity
 */
public class TopActivity extends Activity {

    @Bind(R.id.activity_bottom_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_bottom_ship_image)
    ImageView imageView;

    @OnClick(R.id.top_activity_annei)
    void anneiClick(View view) {
        startStatusListTabActivity(Company.ANNEI);
    }

    @OnClick(R.id.top_activity_ykf)
    void ykfClick(View view) {
        startStatusListTabActivity(Company.YKF);
    }

    @OnClick(R.id.top_activity_dream)
    void dreamClick(View view) {
        startStatusListTabActivity(Company.DREAM);
    }

    @OnClick(R.id.top_activity_setting)
    void settinglick(View view) {
        startSettingActivity();
    }

    /**
     * 設定画面に遷移
     */
    private void startSettingActivity() {
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivity(intent);
    }

    /**
     * 一覧タブ画面に遷移
     * 
     * @param company
     */
    private void startStatusListTabActivity(Company company) {
        Intent intent = new Intent(this, StatusListTabActivity.class);
        intent.putExtra(StatusListTabActivity.class.getCanonicalName(), company);
        startActivity(intent);
    }

    /**
     * 時刻表のタップ
     * 
     * @param view
     */
    @OnClick(R.id.top_activity_timetable)
    void timeTableClick(View view) {
        Intent intent = new Intent(this, TimeTableTabActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        AnimationUtil.show(imageView, null);
                    }
                });
    }
}
