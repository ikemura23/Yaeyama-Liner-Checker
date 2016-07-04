
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdView;
import com.ikmr.banbara23.yaeyama_liner_checker.AnalyticsUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.StatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.cache.CacheManager;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.util.CustomTabUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.util.PortUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailDistanceAndTimeView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailPriceDreamKohamaView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailPriceDreamOoharaView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailPriceDreamView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailTopView;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * ドリーム観光の詳細画面フラグメント
 */
public class StatusDetailDreamFragment extends BaseDetailFragment {

    private static final String TAG = Const.FireBaseAnalitycsTag.STATUS_DETAIL_YKF;

    // ButterKnife Bind View --------------------------------------------
    @Bind(R.id.fragment_status_detail_dream_top_view)
    StatusDetailTopView mStatusDetailTopView;

    @Bind(R.id.fragment_dream_time_table_card)
    CardView mDreamTimeCardView;

    @Bind(R.id.fragment_dream_status_detail_progressbar)
    ProgressWheel mProgressBar;

    @Bind(R.id.fragment_dream_status_detail_reload_button)
    Button mReloadButton;

    @Bind(R.id.fragment_status_detail_dream_distance_time_view)
    StatusDetailDistanceAndTimeView mStatusDetailDistanceAndTimeView;

    @Bind(R.id.fragment_status_detail_dream_price_view)
    StatusDetailPriceDreamView mStatusDetailPriceDreamView;

    @Bind(R.id.fragment_status_detail_dream_price_oohara_view)
    StatusDetailPriceDreamOoharaView mStatusDetailPriceDreamOoharaView;

    @Bind(R.id.fragment_status_detail_dream_price_kohama_view)
    StatusDetailPriceDreamKohamaView mStatusDetailPriceDreamKohamaView;

    @Bind(R.id.adView)
    AdView mAdView;

    // ButterKnife OnClick --------------------------------------------
    @OnClick(R.id.view_status_detail_tell_layout)
    void tellClick(View view) {
        startTell();
        AnalyticsUtils.logSelectEvent(TAG, "tell");
    }

    @OnClick(R.id.view_status_detail_web_layout)
    void webClick(View view) {
        startWeb();
        AnalyticsUtils.logSelectEvent(TAG, "web");
    }

    @OnClick(R.id.fragment_dream_status_detail_reload_button)
    void errorReloadClick(View view) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    // ButterKnife BindString --------------------------------------------
    @BindString(R.string.tel_dream)
    String TEL_DREAM;

    @BindString(R.string.hp_dream)
    String HP_DREAM;

    // プライベート変数 --------------------------------------------
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public static StatusDetailDreamFragment NewInstance(YkfLinerDetail ykfLinerDetail) {
        StatusDetailDreamFragment fragment = new StatusDetailDreamFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailDreamFragment.class.getName(), ykfLinerDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_dream, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
        startQuery();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mCompositeSubscription.unsubscribe();
    }

    /**
     * 各Viewの初期設定
     */
    private void initViews() {
        initAdView(mAdView);

        // プレミアムドリーム・スーパードリームは時刻表がないので表示しない
        if (isTimeTableShow()) {
            mDreamTimeCardView.setVisibility(View.VISIBLE);
            initTimeTableView();
        }
        initPriceView();
        initDistanceAndTimeView();
    }

    /**
     * 時刻表の初期設定
     */
    private void initTimeTableView() {
        mDreamTimeCardView.removeAllViews();
        int viewResourceId = getTimeTableLayoutResourceId();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(viewResourceId, mDreamTimeCardView);
    }

    /**
     * 表示する港のViewを取得
     *
     * @return 港のリソースID
     */
    private int getTimeTableLayoutResourceId() {

        switch (getParam().getPort()) {
            case TAKETOMI:
                return R.layout.view_time_table_dream_taketomi;
            case KOHAMA:
                return R.layout.view_time_table_dream_kohama;
            case KUROSHIMA:
                return R.layout.view_time_table_dream_kuroshima;
            case OOHARA:
                return R.layout.view_time_table_dream_oohara;
            case HATOMA_UEHARA:
                return R.layout.view_time_table_dream_uehara;
            default:
                return R.layout.view_time_table_ykf_taketomi;
        }
    }

    /**
     * 値段Viewの初期設定
     */
    private void initPriceView() {
        // 値段の設定、大原と小浜は値段のレイアウトが違う
        switch (getParam().getPort()) {
            case KOHAMA:
                mStatusDetailPriceDreamKohamaView.setVisibility(View.VISIBLE);
                break;
            case OOHARA:
                mStatusDetailPriceDreamOoharaView.setVisibility(View.VISIBLE);
                break;
            case SUPER_DREAM:
                mStatusDetailDistanceAndTimeView.setVisibility(View.GONE);
                break;
            case PREMIUM_DREAM:
                mStatusDetailDistanceAndTimeView.setVisibility(View.GONE);
                break;
            default:
                mStatusDetailPriceDreamView.setVisibility(View.VISIBLE);
                mStatusDetailPriceDreamView.setLinerPrice(getLinerPrice());
                mStatusDetailPriceDreamView.setFerryPrice(getFerryPrice());

                mStatusDetailDistanceAndTimeView.setDistanceText(null);
                mStatusDetailDistanceAndTimeView.setTimeText(getTime());
                return;
        }
    }

    /**
     * 走行時間、距離の初期設定
     */
    private void initDistanceAndTimeView() {
        // 走行時間と距離、ドリームは距離を公開していないのでnullを入れて非表示にする
        mStatusDetailDistanceAndTimeView.setDistanceText(null);
        mStatusDetailDistanceAndTimeView.setTimeText(getTime());
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private YkfLinerDetail getParam() {
        return getArguments().getParcelable(StatusDetailDreamFragment.class.getName());
    }

    /**
     * 時刻表を表示する港か？ （プレミアムドリームとスーパードリームは時刻表を持っていないので表示しない）
     *
     * @return true:表示 false:非表示
     */
    private boolean isTimeTableShow() {
        return getParam().getPort() != Port.PREMIUM_DREAM && getParam().getPort() != Port.SUPER_DREAM;
    }

    /**
     * 外部電話アプリ起動
     */
    private void startTell() {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("tel:" + TEL_DREAM));

            startActivity(intent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 外部ブラウザアプリ起動
     */
    private void startWeb() {
        CustomTabUtil.start(getActivity(), HP_DREAM);
    }

    /**
     * ステータス取得処理の開始
     */
    public void startQuery() {
        mProgressBar.setVisibility(View.VISIBLE);
        mStatusDetailTopView.setVisibility(View.GONE);
        // キャッシュ処理
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.isPreferenceCacheDisable() || cacheManager.isExpiryList(Company.DREAM)) {
            // キャッシュが無効なので通信必要
            startApiQuery();
            return;
        }
        // キャッシュ有効なので不要
        Result result = cacheManager.getListResultCache(Company.DREAM);
        onResultListQuery(result);
        finishQuery();
    }

    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    private Price getLinerPrice() {
        return PortUtil.getDreamLinerPrice(getContext(), getParam().getPort());
    }

    private Price getFerryPrice() {
        return PortUtil.getDreamFerryPrice(getContext(), getParam().getPort());
    }

    private String getTime() {
        return PortUtil.getDreamTime(getContext(), getParam().getPort());
    }

    /**
     * 八重山観光フェリーAPIを呼び出す
     */
    private void startApiQuery() {

        mCompositeSubscription.add(
                StatusListApi.request(Company.DREAM)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                // 完了
                                finishQuery();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 失敗
                                failedQuery(e);
                            }

                            @Override
                            public void onNext(Result result) {
                                // 値うけとる
                                onResultListQuery(result);
                            }
                        })
        );
    }

    /**
     * 一覧を取得した
     *
     * @param result
     */
    private void onResultListQuery(Result result) {
        mStatusDetailTopView.setUpdateText(result.getUpdateTime());
        Liner liner = PortUtil.getMyPort(result.getLiners(), getParam().getPort());
        mStatusDetailTopView.bindStatus(liner);
    }

    /**
     * 取得失敗
     *
     * @param e
     */
    public void failedQuery(Throwable e) {
        mReloadButton.setVisibility(View.VISIBLE);
        Crashlytics.logException(e);
    }

    /**
     * 取得完了
     */
    public void finishQuery() {
        mProgressBar.setVisibility(View.GONE);
        mStatusDetailTopView.setVisibility(View.VISIBLE);
    }
}
