
package com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 時刻表 Activity
 */
public class DreamTimeTableActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<View> mPortViewList;

    @Bind(R.id.activity_timetable_ritou)
    TextView mRitouText;

    @Bind(R.id.activity_timetable_spinner)
    Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mSpinner.setOnItemSelectedListener(this);
        createPortArray();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("timetable_spinner", mSpinner.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            int index = savedInstanceState.getInt("timetable_spinner");
            getSpinner().setSelection(index);
            createPortArray();
        } catch (Exception e) {
            // 処理なし
        }
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

    private void createPortArray() {
        mPortViewList = new ArrayList<>();
        mPortViewList.add(findViewById(R.id.activity_timetable_taketomi));
        mPortViewList.add(findViewById(R.id.activity_timetable_kuroshima));
        mPortViewList.add(findViewById(R.id.activity_timetable_kohama));
        mPortViewList.add(findViewById(R.id.activity_timetable_uehara));
        mPortViewList.add(findViewById(R.id.activity_timetable_oohara));
        mPortViewList.add(findViewById(R.id.activity_timetable_hateruma));
        mPortViewList.add(findViewById(R.id.activity_timetable_hatoma));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // 選択した港を画面表示
        String selectedItem = (String) parent.getItemAtPosition(position);
        getRitouText().setText(selectedItem + "発");

        // View切り替え
        changePortView(position);
    }

    /**
     * View切り替え
     * 
     * @param position スピナー選択番号
     */
    private void changePortView(int position) {
        for (View view : mPortViewList) {
            if (view == mPortViewList.get(position)) {
                view.setVisibility(View.VISIBLE);
            }
            else {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private TextView getRitouText() {
        if (mRitouText == null) {
            mRitouText = (TextView) findViewById(R.id.activity_timetable_ritou);
        }
        return mRitouText;
    }

    private Spinner getSpinner() {
        if (mSpinner == null) {
            mSpinner = (Spinner) findViewById(R.id.activity_timetable_spinner);
            mSpinner.setOnItemSelectedListener(this);
        }
        return mSpinner;
    }
}
