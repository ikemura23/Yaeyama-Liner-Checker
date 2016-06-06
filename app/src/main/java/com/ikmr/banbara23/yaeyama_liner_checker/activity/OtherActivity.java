
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.BuildConfig;
import com.ikmr.banbara23.yaeyama_liner_checker.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/**
 * アプリ情報Activity
 */
public class OtherActivity extends BaseActivity {

    @Bind(R.id.activity_other_version_name)
    TextView mVersionNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        mVersionNameText.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    // /**
    // * メール問い合わせボタン押下
    // *
    // * @param view
    // */
    // public void mailClick(View view) {
    //
    // Intent intent = new Intent();
    // intent.setAction(Intent.ACTION_SENDTO);
    // String mailAddress = getString(R.string.mail_address);
    // intent.setData(Uri.parse("mailto:" + mailAddress));
    //
    // String subject = getString(R.string.app_name) +
    // getString(R.string.EXTRA_SUBJECT);
    // intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    //
    // try {
    // startActivity(intent);
    // } catch (Exception e) {
    // Toast.makeText(this, "メール起動でエラーが発生しました", Toast.LENGTH_SHORT).show();
    // }
    // }

    /**
     * アンケートボタン押下
     *
     * @param view
     */
    public void formClick(View view) {

        Uri uri = Uri.parse(getString(R.string.form_address));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 評価ボタン押下
     *
     * @param view
     */
    public void playStoreClick(View view) {

        Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW);
        googlePlayIntent.setData(Uri.parse("market://details?id=com.banbara.yaeyama.liner.checker"));
        try {
            startActivity(googlePlayIntent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * ライセンスクリック
     * 
     * @param view
     */
    public void licenseClick(View view) {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Material-ish Progress", "https://github.com/pnikosis/materialish-progress", "Copyright 2014 Nico Hormazábal", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Butter Knife", "https://github.com/JakeWharton/butterknife", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("RxJava", "https://github.com/ReactiveX/RxJava", "Copyright 2013 Netflix, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("RxAndroid", "https://github.com/ReactiveX/RxAndroid", "Copyright 2015 The RxAndroid authors", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Material Ripple Layout", "https://github.com/balysv/material-ripple", "Copyright 2015 Balys Valentukevicius", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("moshi", "https://github.com/square/moshi", "Copyright 2015 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("timber", "https://github.com/JakeWharton/timber", "Copyright 2013 Jake Wharton", new ApacheSoftwareLicense20()));
        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }
}
