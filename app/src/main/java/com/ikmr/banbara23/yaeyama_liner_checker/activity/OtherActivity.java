
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

    /**
     * 八重山観光フェリークリック
     *
     * @param view
     */
    public void mailClick(View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        String mailAddress = getString(R.string.mail_address);
        intent.setData(Uri.parse("mailto:" + mailAddress));

        String subject = getString(R.string.title_activity_top) + getString(R.string.EXTRA_SUBJECT);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "メール起動でエラーが発生しました", Toast.LENGTH_SHORT).show();
        }
    }

}
