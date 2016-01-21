
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

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

    /**
     * メール問い合わせボタン押下
     *
     * @param view
     */
    public void mailClick(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        String mailAddress = getString(R.string.mail_address);
        intent.setData(Uri.parse("mailto:" + mailAddress));

        String subject = getString(R.string.app_name) + getString(R.string.EXTRA_SUBJECT);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "メール起動でエラーが発生しました", Toast.LENGTH_SHORT).show();
        }
    }

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
            // 何もしない
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
            // 何もしない
        }
    }
}
