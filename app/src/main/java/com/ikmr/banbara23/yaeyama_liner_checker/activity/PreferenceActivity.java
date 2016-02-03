
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.os.Bundle;

import com.ikmr.banbara23.yaeyama_liner_checker.fragment.SettingFragment;

/**
 * 設定画面のアクティビティ
 */
public class PreferenceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .add(android.R.id.content, new SettingFragment())
                .commit();
    }
}
