
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
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

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.YkfStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.util.PortUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailDistanceAndTimeView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailPriceView;
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
 * 詳細のフラグメント
 */
public class StatusDetailYkfFragment extends BaseDetailFragment {

    @Bind(R.id.fragment_status_detail_ykf_top_view)
    StatusDetailTopView mStatusDetailTopView;

    @Bind(R.id.fragment_time_table_view)
    YkfTimeTableView mYkfTimeTableView;

    @Bind(R.id.fragment_status_detail_ykf_content_layout)
    LinearLayout mFragmentStatusDetailContentLayout;

    @Bind(R.id.fragment_ykf_status_detail_progressbar)
    ProgressWheel mProgressBar;

    @Bind(R.id.fragment_status_detail_ykf_distance_time_view)
    StatusDetailDistanceAndTimeView mStatusDetailDistanceAndTimeView;

    @Bind(R.id.fragment_status_detail_ykf_price_view)
    StatusDetailPriceView mStatusDetailPriceView;

    @Bind(R.id.fragment_ykf_status_detail_reload_button)
    Button mReloadButton;

    @OnClick(R.id.fragment_ykf_status_detail_reload_button)
    void reloadClick(View view) {
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
        startTell();
    }

    @BindString(R.string.url_ykf_list)
    String URL_YKF_LIST;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public static StatusDetailYkfFragment NewInstance(YkfLinerDetail ykfLinerDetail) {
        StatusDetailYkfFragment fragment = new StatusDetailYkfFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailYkfFragment.class.getName(), ykfLinerDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private YkfLinerDetail getParam() {
        return getArguments().getParcelable(StatusDetailYkfFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_ykf, container, false);
        ButterKnife.bind(this, view);
        mAdView = ButterKnife.findById(view, R.id.adView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startQuery();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mCompositeSubscription.unsubscribe();
    }

    /**
     * 外部電話アプリ起動
     */
    private void startTell() {
        String tell = getActivity().getApplicationContext().getString(R.string.tel_ykf);
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("tel:" + tell));
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 何もしない
        }
    }

    /**
     * 外部ブラウザ起動
     */
    private void startWeb() {
        String hpUrl = getActivity().getApplicationContext().getString(R.string.hp_annei);
        Uri uri = Uri.parse(hpUrl);
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
        getYkfList();
        mStatusDetailDistanceAndTimeView.setDistanceText(null);
        mStatusDetailDistanceAndTimeView.setTimeText(getTime());
        mStatusDetailPriceView.setPrice(getPrice());
    }

    private String getTime() {
        return PortUtil.getYkfTime(getContext(), getParam().getPort());
    }

    private Price getPrice() {
        return PortUtil.getYkfPrice(getContext(), getParam().getPort());
    }

    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * 八重山観光フェリーAPIを呼び出す
     */
    private void getYkfList() {

        mCompositeSubscription.add(
                YkfStatusListApi.request(URL_YKF_LIST)
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
        Liner liner = PortUtil.getMyPort(result.getLiners(), getParam().getPort());
        mStatusDetailTopView.bindStatus(liner);
        mStatusDetailTopView.setUpdateText(result.getUpdateTime());
    }

    /**
     * 取得失敗
     */
    public void failedQuery() {
        mReloadButton.setVisibility(View.VISIBLE);
    }

    /**
     * 取得完了
     */
    public void finishQuery() {
        mProgressBar.setVisibility(View.GONE);
        mFragmentStatusDetailContentLayout.setVisibility(View.VISIBLE);
        mYkfTimeTableView.switchPortView(getParam().getPort());
    }
}
