
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.DreamStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.cache.CacheManager;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream.DreamTimeTableView;
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

    // ButterKnife Bind View --------------------------------------------
    @Bind(R.id.fragment_status_detail_dream_top_view)
    StatusDetailTopView mStatusDetailTopView;

    @Bind(R.id.fragment_dream_time_table_view)
    DreamTimeTableView mDreamTimeTableView;

    @Bind(R.id.fragment_dream_status_detail_content_layout)
    LinearLayout mFragmentDreamStatusDetailContentLayout;

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

    // ButterKnife OnClick --------------------------------------------
    @OnClick(R.id.view_status_detail_tell_layout)
    void tellClick(View view) {
        startTell();
    }

    @OnClick(R.id.view_status_detail_web_layout)
    void webClick(View view) {
        startWeb();
    }

    @OnClick(R.id.fragment_dream_status_detail_reload_button)
    void errorReloadClick(View view) {
        // TODO: 16/01/05 エラー再読み込みボタン押下
    }

    // ButterKnife BindString --------------------------------------------
    @BindString(R.string.url_dream_list)
    String URL_DREAM_LIST;

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
        mAdView = ButterKnife.findById(view, R.id.adView);
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

        // プレミアムドリーム・スーパードリームは時刻表がないので表示しない
        if (isTimeTableShow()) {
            mDreamTimeTableView.setVisibility(View.VISIBLE);
            mDreamTimeTableView.switchPortView(getParam().getPort());
        }

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
     * 時刻表を表示する港か？ （プレミアムドリームとスーパードリームはじ時刻表を持っていないので表示しない）
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
            // 何もしない
        }
    }

    /**
     * 外部ブラウザアプリ起動
     */
    private void startWeb() {
        Uri uri = Uri.parse(HP_DREAM);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 何もしない
        }

    }

    /**
     * ステータス取得処理の開始
     */
    public void startQuery() {
        mProgressBar.setVisibility(View.VISIBLE);
        mFragmentDreamStatusDetailContentLayout.setVisibility(View.GONE);

        // キャッシュ処理
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.isExpiryList(Company.DREAM)) {
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
                DreamStatusListApi.request(URL_DREAM_LIST)
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
                                failedQuery();
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
     */
    public void failedQuery() {
        Crashlytics.logException(new Exception("Dream Status Detail Api Failed"));
        mReloadButton.setVisibility(View.VISIBLE);
    }

    /**
     * 取得完了
     */
    public void finishQuery() {
        mProgressBar.setVisibility(View.GONE);
        mFragmentDreamStatusDetailContentLayout.setVisibility(View.VISIBLE);
    }
}
