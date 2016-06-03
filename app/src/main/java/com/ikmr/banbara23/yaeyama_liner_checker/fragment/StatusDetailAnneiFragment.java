
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
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.AnneiStatusDetailApi;
import com.ikmr.banbara23.yaeyama_liner_checker.api.StatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.cache.CacheManager;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.util.PortUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailDistanceAndTimeView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailPriceHandicappedView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailTopView;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mrapp.android.dialog.MaterialDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 安栄の詳細フラグメント
 */
public class StatusDetailAnneiFragment extends BaseDetailFragment {

    // ButterKnife Bind View --------------------------------------------
    @Bind(R.id.fragment_status_detail_progressbar)
    ProgressWheel mProgressBar;

    @Bind(R.id.fragment_status_detail_reload_button)
    Button mFragmentStatusDetailErrorButton;

    @Bind(R.id.fragment_status_detail_top_view)
    StatusDetailTopView mStatusDetailTopView;

    // @Bind(R.id.fragment_time_table_annei_view)
    // AnneiTimeTableView mAnneiTimeTableView;

    @Bind(R.id.fragment_time_annei_table_timetable_view)
    CardView mTimeTableLayout;

    @Bind(R.id.fragment_status_detail_distance_time_view)
    StatusDetailDistanceAndTimeView mDistanceTimeView;

    @Bind(R.id.fragment_status_detail_price_view)
    StatusDetailPriceHandicappedView mPriceView;

    // ButterKnife OnClick --------------------------------------------

    /**
     * もっと見るボタン押下
     *
     * @param view
     */
    @OnClick(R.id.view_status_detail_top_comment_more_button)
    void MoreButtonClick(View view) {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_status_detail_comment, null);
        ((TextView) dialogView.findViewById(R.id.dialog_status_detail_comment_text)).setText(dialogMessage);
        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.detail_dialog_title);
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        dialogBuilder.setView(dialogView);
        MaterialDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * エラー時の再読み込みボタン押下
     *
     * @param view
     */
    @OnClick(R.id.fragment_status_detail_reload_button)
    void reloadButtonClick(View view) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    /**
     * 電話する押下
     *
     * @param view
     */
    @OnClick(R.id.view_status_detail_web_layout)
    void telClick(View view) {
        startWeb();
    }

    /**
     * サイトを見る押下
     *
     * @param view
     */
    @OnClick(R.id.view_status_detail_tell_layout)
    void webClick(View view) {
        startTel();
    }

    // ButterKnife BindString --------------------------------------------
    // 一覧URL
    @BindString(R.string.url_annei_list)
    String ANNEI_LIST_URL;

    // 電話番号
    @BindString(R.string.tel_annei)
    String TEL_ANNEI;

    // プライベート変数 --------------------------------------------
    private boolean listQuerying = false;
    private boolean detailQuerying = false;
    private String dialogMessage;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public static StatusDetailAnneiFragment NewInstance(Liner liner) {
        StatusDetailAnneiFragment fragment = new StatusDetailAnneiFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailAnneiFragment.class.getName(), liner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        startQuery();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_annei, container, false);
        ButterKnife.bind(this, view);
        mAdView = ButterKnife.findById(view, R.id.adView);
        setTimeTableView();
        return view;
    }

    private void setTimeTableView() {
        mTimeTableLayout.removeAllViews();
        int viewResourceId = getAnneiTimeTableLayoutResourceId();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(viewResourceId, mTimeTableLayout);
    }

    private int getAnneiTimeTableLayoutResourceId() {

        switch (getPort()) {
            case TAKETOMI:
                return R.layout.view_time_table_annei_taketomi;
            case KOHAMA:
                return R.layout.view_time_table_annei_kohama;
            case KUROSHIMA:
                return R.layout.view_time_table_annei_kuroshima;
            case OOHARA:
                return R.layout.view_time_table_annei_oohara;
            case UEHARA:
                return R.layout.view_time_table_annei_uehara;
            case HATOMA:
                return R.layout.view_time_table_annei_hatoma;
            case HATERUMA:
                return R.layout.view_time_table_annei_hateruma;
            default:
                return R.layout.view_time_table_annei_taketomi;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mCompositeSubscription.unsubscribe();
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private Liner getParam() {
        return getArguments().getParcelable(StatusDetailAnneiFragment.class.getName());
    }

    private Port getPort() {
        return getParam().getPort();
    }

    /**
     * 取得の開始
     */
    public void startQuery() {
        mStatusDetailTopView.setVisibility(View.GONE);
        mFragmentStatusDetailErrorButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        // mAnneiTimeTableView.switchPortView(getPort());

        if (getActivity() != null && getActivity() instanceof FragmentApiQueryInterface) {
            // API通信処理の開始準備の完了
            ((FragmentApiQueryInterface) getActivity()).startQuery();
        }

        // 処理フラグをON
        listQuerying = true;
        detailQuerying = true;

        startDetailQuery();
        startListQuery();
    }

    /**
     * 取得処理の手前でキャッシュ取得か、通信するかの判定
     */
    private void startDetailQuery() {
        // キャッシュ処理
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.isPreferenceCacheDisable() || cacheManager.isExpiryDetailAnnei(getPort())) {
            // キャッシュが無効なので通信必要
            startAnneiDetailQuery();
            return;
        }
        // キャッシュ有効なので不要
        String comment = cacheManager.getDetailAnneiResultCache();
        onResultDetailQuery(comment);
        detailQuerying = false;
        finishQuery();
    }

    /**
     * 安栄のTOPの一覧を取得
     */
    private void startAnneiDetailQuery() {
        String url = PortUtil.getAnneiDetailUrl(getActivity().getApplicationContext(), getPort());
        mCompositeSubscription.add(
                AnneiStatusDetailApi.request(url)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                // 完了
                                detailQuerying = false;
                                finishQuery();
                            }

                            @Override
                            public void onError(Throwable e) {
                                detailQuerying = false;
                                Crashlytics.logException(e);
                            }

                            @Override
                            public void onNext(String s) {
                                // 値うけとる
                                onResultDetailQuery(s);
                                saveResultDetailToCache(s);
                            }
                        })
                );
    }

    /**
     * 通信で取得した詳細をキャッシュに保存
     *
     * @param value 詳細
     */
    private void saveResultDetailToCache(String value) {
        CacheManager.getInstance().saveNowDetailAnneiTimeStamp(getPort());
        CacheManager.getInstance().putDetail(getPort(), value);
    }

    /**
     * 安栄の港の詳細を取得
     */
    private void startListQuery() {
        // キャッシュ処理
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.isExpiryList(Company.ANNEI)) {
            // キャッシュが無効なので通信必要
            startAnneiListQuery();
            return;
        }
        // キャッシュ有効なので不要
        Result result = cacheManager.getListResultCache(Company.ANNEI);
        onResultListQuery(result);
        listQuerying = false;
        finishQuery();
    }

    /**
     * 通信で安栄の一覧を取得
     */
    private void startAnneiListQuery() {
        mCompositeSubscription.add(
                StatusListApi.request(Company.ANNEI)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                // 完了
                                listQuerying = false;
                                finishQuery();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 失敗
                                listQuerying = false;
                                failedQuery(e);
                            }

                            @Override
                            public void onNext(Result result) {
                                // 値うけとる
                                onResultListQuery(result);
                                saveResultListToCache(result);
                            }
                        })
                );
    }

    /**
     * 通信した結果をキャッシュに保存
     *
     * @param result 通信値
     */
    private void saveResultListToCache(Result result) {
        CacheManager.getInstance().saveNowListTimeStamp(Company.ANNEI);
        CacheManager.getInstance().putResult(Company.ANNEI, result);
    }

    /**
     * 一覧を取得した
     *
     * @param result
     */
    private void onResultListQuery(Result result) {
        if (result == null || result.getLiners().isEmpty() || result.getLiners().size() == 0) {
            failedQuery(new Exception("AnneiDetailApiQueryError : Annei status list api result a Null or Empty"));
            return;
        }

        Liner liner = PortUtil.getMyPort(result.getLiners(), getPort());

        mStatusDetailTopView.bindStatus(liner);
        mStatusDetailTopView.setUpdateText(result.getUpdateTime());

        mDistanceTimeView.setDistanceText(getAnneiDistance());
        mDistanceTimeView.setTimeText(getAnneiTime());

        mPriceView.setPrice(getPrice());
    }

    /**
     * 詳細を取得した
     *
     * @param comment
     */
    private void onResultDetailQuery(String comment) {
        if (comment == null) {
            mStatusDetailTopView.setVisibility(View.GONE);
            return;
        }
        mStatusDetailTopView.setCommentText(comment);
        dialogMessage = comment;
    }

    /**
     * 走行距離
     *
     * @return 距離
     */
    private String getAnneiDistance() {
        return PortUtil.getAnneiDistance(getContext(), getPort());
    }

    /**
     * 走行時間
     *
     * @return 走行時間
     */
    private String getAnneiTime() {
        return PortUtil.getAnneiTime(getContext(), getPort());
    }

    /**
     * 料金・大人
     *
     * @return 料金・大人
     */
    public Price getPrice() {
        return PortUtil.getAnneiPrice(getContext(), getPort());
    }

    /**
     * 取得失敗
     *
     * @param e
     */
    public void failedQuery(Throwable e) {
        Crashlytics.logException(e);
        mProgressBar.setVisibility(View.GONE);
        mFragmentStatusDetailErrorButton.setVisibility(View.VISIBLE);
    }

    /**
     * 取得完了
     */
    public void finishQuery() {
        if (listQuerying || detailQuerying) {
            return;
        }
        mProgressBar.setVisibility(View.GONE);
        mStatusDetailTopView.setVisibility(View.VISIBLE);
        if (getActivity() != null && getActivity() instanceof FragmentApiQueryInterface) {
            // API通信処理の開始準備の完了
            ((FragmentApiQueryInterface) getActivity()).finishQuery();
        }
    }

    /**
     * 外部電話アプリを起動
     */
    private void startTel() {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("tel:" + TEL_ANNEI));

            startActivity(intent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    /**
     * 外部ブラウザを起動
     */
    private void startWeb() {
        String urlString = PortUtil.getAnneiDetailUrl(getContext(), getPort());
        Uri uri = Uri.parse(urlString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }
}
