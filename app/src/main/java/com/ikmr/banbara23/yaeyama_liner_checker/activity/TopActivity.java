package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.util.AnimationUtil;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * トップActivity
 */
public class TopActivity extends Activity {

    @Bind(R.id.activity_bottom_ship_image)
    ImageView shipView;

    @OnClick(R.id.activity_bottom_ship_image)
    void shipClick(View view) {
        startShipRandomAnimation();
    }

    private void startShipRandomAnimation() {
        try {
            YoYo.with(getRandomTechniques()).playOn(shipView);
        }
        catch (Exception e) {
        }
    }

    private Techniques getRandomTechniques() {
        Random random = new Random();
        switch (random.nextInt(11)) {
            case 0:
                return Techniques.Linear;
            case 1:
                return Techniques.Pulse;
            case 2:
                return Techniques.RubberBand;
            case 3:
                return Techniques.Shake;
            case 4:
                return Techniques.Swing;
            case 5:
                return Techniques.Wobble;
            case 6:
                return Techniques.Bounce;
            case 7:
                return Techniques.Tada;
            case 8:
                return Techniques.StandUp;
            case 9:
                return Techniques.Wave;
            case 10:
                return Techniques.BounceInLeft;
            default:
                return Techniques.Shake;
        }
    }

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
//        firstShowAnimationForShipImage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        YoYo.with(Techniques.SlideInLeft).repeat(0).playOn(shipView);
    }

    /**
     * 画面表示時に船のアニメーション表示
     */
    private void firstShowAnimationForShipImage() {
        View view = findViewById(R.id.activity_bottom_toolbar);
        if (view == null)
            return;
        if (shipView == null)
            return;
        shipView.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        AnimationUtil.show(shipView, null);
//                        shipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }
}
