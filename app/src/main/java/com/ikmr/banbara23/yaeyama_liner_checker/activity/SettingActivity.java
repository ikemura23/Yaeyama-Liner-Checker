
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;
import android.view.View;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 設定画面のアクティビティ
 */
public class SettingActivity extends BaseActivity {

    @OnClick(R.id.top_activity_setting_cache)
    void onSettingClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
}
