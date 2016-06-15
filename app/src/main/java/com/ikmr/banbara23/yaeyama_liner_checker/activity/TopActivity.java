package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableTabActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * トップActivity
 */
public class TopActivity extends Activity {

    FirebaseAnalytics firebaseAnalytics;

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
        sendLogEvent("setting");
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivity(intent);
    }

    /**
     * 一覧タブ画面に遷移
     *
     * @param company
     */
    private void startStatusListTabActivity(Company company) {
        sendLogEvent("list");
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
        sendLogEvent("timetable");
        Intent intent = new Intent(this, TimeTableTabActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void sendLogEvent(String buttonName) {
        Bundle bundle = new Bundle();
        bundle.putString("name", buttonName);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
