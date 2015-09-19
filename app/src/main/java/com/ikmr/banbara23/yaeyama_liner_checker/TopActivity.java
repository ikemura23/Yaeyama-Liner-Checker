
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
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
     * 安栄クリック
     * 
     * @param view
     */
    public void aneiClick(View view) {
        Toast.makeText(this, "aneiClick", Toast.LENGTH_SHORT).show();
    }

    /**
     * 八重山観光フェリークリック
     * 
     * @param view
     */
    public void ykfClick(View view) {
        Toast.makeText(this, "ykfClick", Toast.LENGTH_SHORT).show();
    }
}
