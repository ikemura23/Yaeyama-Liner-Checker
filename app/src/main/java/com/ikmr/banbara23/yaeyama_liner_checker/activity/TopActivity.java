
package com.ikmr.banbara23.yaeyama_liner_checker.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.daasuu.bl.BubbleLayout;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.AnalyticsUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.TopInfoApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.api.WeatherApiClient;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Weather;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.CompanyStatus;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.CompanyStatusInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.TopInfo;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableTabActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.util.AnimationUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.util.CustomTabUtil;
import com.orhanobut.logger.Logger;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * トップActivity
 */
public class TopActivity extends Activity {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    static final int RandomNextInt = 11;
    Weather mWeather = null;
    private static final String TAG = Const.FireBaseAnalitycsTag.TOP;

    @Bind(R.id.top_activity_status_annei)
    TextView anneiStatusText;

    @Bind(R.id.top_activity_status_ykf)
    TextView ykfStatusText;

    @Bind(R.id.top_activity_status_dream)
    TextView dreamStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bubbleLayout.setVisibility(View.GONE);
        createBubbleText();
        startTopInfo();
    }

    private void startTopInfo() {
        anneiStatusText.setVisibility(View.GONE);
        ykfStatusText.setVisibility(View.GONE);
        dreamStatusText.setVisibility(View.GONE);

        topProgressBar.setVisibility(View.VISIBLE);
        new TopInfoApiClient().request()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<TopInfo>() {
                    @Override
                    public void onCompleted() {
                        topProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.logException(e);
                        topProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(TopInfo topInfo) {
                        changeCompanyStatus(topInfo.getCompanyStatusInfo());
                        Logger.json(new Gson().toJson(topInfo, TopInfo.class));
                    }
                });
    }

    private void changeCompanyStatus(CompanyStatusInfo companyStatusInfo) {
        setCompanyStatusView(companyStatusInfo.getAneiStatus(), anneiStatusText);
        setCompanyStatusView(companyStatusInfo.getYkfStatus(), ykfStatusText);
        setCompanyStatusView(companyStatusInfo.getDreamStatus(), dreamStatusText);
    }

    private void setCompanyStatusView(CompanyStatus companyStatus, TextView textView) {
        textView.setVisibility(View.VISIBLE);
        switch (companyStatus.getStatus()) {
            case NORMAL:
                textView.setText(getString(R.string.top_status_normal));
                textView.setTextColor(ContextCompat.getColor(this, R.color.status_normal));
                break;
            case CAUTION:
                textView.setText(getString(R.string.top_status_cation));
                textView.setTextColor(ContextCompat.getColor(this, R.color.status_cation));
                break;
            case CANCEL:
                textView.setText(getString(R.string.top_status_cancel));
                textView.setTextColor(ContextCompat.getColor(this, R.color.status_cancel));
                break;
            case SUSPEND:
                textView.setText(getString(R.string.top_status_suspend));
                textView.setTextColor(ContextCompat.getColor(this, R.color.status_cancel));
                break;
            default:
                textView.setText(getString(R.string.top_status_cation));
                textView.setTextColor(ContextCompat.getColor(this, R.color.status_cation));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AnalyticsUtils.logAppOpenEvent(TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    // butter knife --------------------------------------

    @Bind(R.id.activity_bottom_ship_image)
    ImageView shipView;

    @Bind(R.id.activity_top_bubble)
    BubbleLayout bubbleLayout;

    @Bind(R.id.activity_top_weather_text)
    TextView weatherText;

    @Bind(R.id.activity_top_progress_bar)
    View topProgressBar;

    @OnClick(R.id.activity_bottom_ship_image)
    void shipClick(View view) {
        startShipRandomAnimation();
        AnalyticsUtils.logSelectEvent(TAG, "ship");
    }

    FirebaseAnalytics firebaseAnalytics;

    @OnClick(R.id.activity_top_description)
    void labelClick(View view) {
        startStatusListTabActivity(Company.ANNEI);
        AnalyticsUtils.logSelectEvent(TAG, "label");
    }

    @OnClick(R.id.top_activity_annei)
    void anneiClick(View view) {
        startStatusListTabActivity(Company.ANNEI);
        AnalyticsUtils.logSelectEvent(TAG, "annei");
    }

    @OnClick(R.id.top_activity_ykf)
    void ykfClick(View view) {
        startStatusListTabActivity(Company.YKF);
        AnalyticsUtils.logSelectEvent(TAG, "ykf");
    }

    @OnClick(R.id.top_activity_dream)
    void dreamClick(View view) {
        startStatusListTabActivity(Company.DREAM);
        AnalyticsUtils.logSelectEvent(TAG, "dream");
    }

    /**
     * 設定画面に遷移
     */
    @OnClick(R.id.top_activity_setting)
    void settinglick(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        AnalyticsUtils.logSelectEvent(TAG, "setting");
    }

    /**
     * 時刻表のタップ
     *
     * @param view
     */
    @OnClick(R.id.top_activity_timetable)
    void timeTableClick(View view) {
        Intent intent = new Intent(this, TimeTableTabActivity.class);
        AnalyticsUtils.logSelectEvent(TAG, "timetable");
        startActivity(intent);
    }

    /**
     * 天気タップ
     *
     * @param view
     */
    @OnClick(R.id.activity_top_bubble)
    void bubbleClick(View view) {
        if (mWeather == null) {
            return;
        }
        AnalyticsUtils.logSelectEvent(TAG, "weather");
        CustomTabUtil.start(this, mWeather.getUrl());
    }

    // private method -------------------------------------------

    /**
     * 一覧タブ画面に遷移
     *
     * @param company
     */
    private void startStatusListTabActivity(Company company) {
        Intent intent = new Intent(this, StatusListTabActivity.class);
        intent.putExtra(StatusListTabActivity.class.getCanonicalName(), company);
        startActivity(intent);
        AnalyticsUtils.logSelectEvent(TAG, "list");
    }

    /**
     * 画面表示時に船のアニメーション表示
     */
    private void firstShowAnimationForShipImage() {
        View view = findViewById(R.id.activity_bottom_view);
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
                        // shipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    /**
     * 船のアニメーション開始
     */
    private void startShipRandomAnimation() {
        try {
            YoYo.with(getRandomTechniques()).listen(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    bubbleLayout.setVisibility(View.VISIBLE);
                    // createBubbleText();
                }
            }).playOn(shipView);
        } catch (Exception e) {
            // 処理なし
        }
    }

    /**
     * ランダムなアニメーションを返す
     *
     * @return アニメーション種類
     */
    private Techniques getRandomTechniques() {
        Random random = new Random();
        switch (random.nextInt(RandomNextInt)) {
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

    /**
     * 天気取得して表示
     */
    private void createBubbleText() {
        mCompositeSubscription.add(
                new WeatherApiClient().request()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<Weather>() {
                            @Override
                            public void onCompleted() {
                                bubbleLayout.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Crashlytics.logException(e);
                            }

                            @Override
                            public void onNext(Weather weather) {
                                displayWeather(weather);
                                mWeather = weather;
                            }
                        })
        );
    }

    private void displayWeather(Weather weather) {
        String value = weather.getWind() + "、波" + weather.getWave();
        weatherText.setText(value);
    }
}
