
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikmr.banbara23.yaeyama_liner_checker.PortUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.AnneiStatusDetailApi;
import com.ikmr.banbara23.yaeyama_liner_checker.api.AnneiStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei.AnneiTimeTableView;
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
 * 安栄の詳細フラグメント
 */
public class StatusDetailAnneiFragment extends BaseFragment {

    // ButterKnife Bind View --------------------------------------------

    @Bind(R.id.fragment_status_detail_progressbar)
    ProgressWheel mProgressBar;

    @Bind(R.id.fragment_status_detail_reload_button)
    Button mFragmentStatusDetailErrorButton;

    @Bind(R.id.fragment_status_detail_top_view)
    StatusDetailTopView mStatusDetailTopView;

    @Bind(R.id.fragment_time_table_annei_view)
    AnneiTimeTableView mAnneiTimeTableView;

    @Bind(R.id.fragment_status_detail_distance_time_view)
    StatusDetailDistanceAndTimeView mDistanceTimeView;

    @Bind(R.id.fragment_status_detail_price_view)
    StatusDetailPriceView mPriceView;

    @Bind(R.id.view_status_detail_top_distance_comment)
    TextView mViewStatusDetailTopDistanceComment;

    @Bind(R.id.view_status_detail_top_time_comment)
    TextView mViewStatusDetailTopTimeComment;

    @Bind(R.id.fragment_status_detail_value_layout)
    LinearLayout mFragmentStatusDetailValueLayout;

    // ButterKnife OnClick --------------------------------------------
    // リロード押下
    @OnClick(R.id.fragment_status_detail_reload_button)
    void reloadButtonClick(View view) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    // 電話する押下
    @OnClick(R.id.view_action_box_tel)
    void telClick(View view) {
        startWeb();
    }

    // サイトを見る押下
    @OnClick(R.id.view_action_box_web)
    void webClick(View view) {
        startTel();
    }

    // ButterKnife BindString --------------------------------------------
    // 一覧URL
    @BindString(R.string.url_annei_list)
    String ANNEI_LIST_URL;

    @BindString(R.string.tel_annei)
    String TEL_ANNEI;

    // プライベート変数 --------------------------------------------
    private boolean listQuerying = false;
    private boolean detailQuerying = false;

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
        return view;
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
        mFragmentStatusDetailErrorButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mStatusDetailTopView.setVisibility(View.GONE);
        mAnneiTimeTableView.setVisibility(View.VISIBLE);
        mAnneiTimeTableView.switchPortView(getPort());

        if (getActivity() != null && getActivity() instanceof FragmentApiQueryInterface) {
            // API通信処理の開始準備の完了
            ((FragmentApiQueryInterface) getActivity()).startQuery();
        }

        // 処理フラグをON
        listQuerying = true;
        detailQuerying = true;

        getAnneiDetail();
        getAnneiList();
    }

    /**
     * 安栄のTOPの一覧を取得
     */
    private void getAnneiDetail() {
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
                                //
                                detailQuerying = false;
                            }

                            @Override
                            public void onNext(String s) {
                                // 値うけとる
                                onResultDetailQuery(s);
                            }
                        })
                );
    }

    /**
     * 安栄の港の詳細を取得
     */
    private void getAnneiList() {
        mCompositeSubscription.add(
                AnneiStatusListApi.request(ANNEI_LIST_URL)
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
        if (result == null || result.getLiners().isEmpty() || result.getLiners().size() == 0) {
            failedQuery();
            return;
        }

        Liner liner = PortUtil.getMyPort(result.getLiners(), getPort());

        mStatusDetailTopView.setVisibility(View.VISIBLE);
        mStatusDetailTopView.bindStatus(liner);
        mDistanceTimeView.setDistanceText(getAnneiDistance());
        mDistanceTimeView.setTimeText(getAnneiTime());
        mPriceView.setPriceAdultText(getAnneiAdultPrice());
        mPriceView.setPriceChildText(getAnneiChildPrice());
        mPriceView.setPriceHandicappedText(getHandicappedPrice());
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
    }

    /**
     * 走行距離
     * 
     * @return 距離
     */
    private String getAnneiDistance() {
        return null;
    }

    /**
     * 走行時間
     * 
     * @return 走行時間
     */
    private String getAnneiTime() {
        return null;
    }

    /**
     * 料金・大人
     * 
     * @return 料金・大人
     */
    private String getAnneiAdultPrice() {
        return null;
    }

    /**
     * 料金・子供
     * 
     * @return 料金・子供
     */
    private String getAnneiChildPrice() {
        return null;
    }

    /**
     * 料金・障害者
     * 
     * @return 料金・障害者
     */
    private String getHandicappedPrice() {
        return null;
    }

    /**
     * 取得失敗
     */
    public void failedQuery() {
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
            // 何もしない
        }
    }

    /**
     * 外部ブラウザを起動
     */
    private void startWeb() {
        String urlString = PortUtil.getAnneiDetailUrl(getActivity().getApplicationContext(), getPort());
        Uri uri = Uri.parse(urlString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 何もしない
        }

    }
}
