
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.StatusDetailAnneiActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.StatusDetailDreamActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.activity.StatusDetailYkfActivity;
import com.ikmr.banbara23.yaeyama_liner_checker.api.AnneiStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.api.DreamStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.api.YkfStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.cache.CacheManager;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.util.StringUtils;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindString;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 一覧タブListFragment
 */
public class StatusListTabFragment extends ListFragment {

    StatusListAdapter mListAdapter;
    TextView mTitleText;
    TextView mUpdateText;
    View mHeaderView;
    ProgressWheel mProgressWheel;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    // ButterKnife BindString --------------------------------------------
    @BindString(R.string.url_annei_list)
    String URL_ANNEI_LIST;

    @BindString(R.string.url_dream_list)
    String URL_DREAM_LIST;

    @BindString(R.string.url_ykf_list)
    String URL_YKF_LIST;

    public static StatusListTabFragment NewInstance(Company company) {
        StatusListTabFragment fragment = new StatusListTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StatusListTabFragment.class.getCanonicalName(), company);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_list, container, false);
        mProgressWheel = (ProgressWheel) view.findViewById(R.id.fragment_status_list_progressbar);
        ButterKnife.bind(this, view);
        initViews();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().addHeaderView(mHeaderView, null, false);
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    private void initViews() {
        mHeaderView = View.inflate(getActivity(), R.layout.fragment_status_list_header_view, null);
        mTitleText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_title_text);
        mUpdateText = (TextView) mHeaderView.findViewById(R.id.fragment_status_list_toolbar_update_text);
        mListAdapter = new StatusListAdapter(getActivity().getApplicationContext());
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private Company getParam() {
        return (Company) getArguments().get(StatusListTabFragment.class.getCanonicalName());
    }

    private void startQuery() {
        mProgressWheel.setVisibility(View.VISIBLE);
        mHeaderView.setVisibility(View.GONE);
        mListAdapter.clear();
        setListAdapter(mListAdapter);

        // キャッシュ処理
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager.isExpiry(getParam())) {
            // キャッシュが無効なので通信必要
            startListQuery();
            return;
        }
        // キャッシュ有効なので不要
        Result result = cacheManager.getResultCache(getParam());
        onResultListQuery(result);
        finishQuery();
    }

    // /**
    // * タイムスタンプのキー取得
    // *
    // * @return key
    // */
    // private String getTimeStampKey() {
    // switch (getParam()) {
    // case ANNEI:
    // return Const.TIMESTAMP_ANNEI_LIST_KEY;
    // case YKF:
    // return Const.TIMESTAMP_YKF_LIST_KEY;
    // case DREAM:
    // return Const.TIMESTAMP_DREAM_LIST_KEY;
    // default:
    // return null;
    // }
    // }
    //
    // /**
    // * 一覧のキャッシュキー取得
    // *
    // * @return key
    // */
    // private String getCacheKey() {
    // switch (getParam()) {
    // case ANNEI:
    // return Const.PREF_ANNEI_LIST_KEY;
    // case YKF:
    // return Const.PREF_YKF_LIST_KEY;
    // case DREAM:
    // return Const.PREF_DREAM_LIST_KEY;
    // default:
    // return null;
    // }
    // }

    /**
     * 一覧の取得処理開始
     */
    private void startListQuery() {
        switch (getParam()) {
            case ANNEI:
                startAnneiListQuery();
                break;
            case YKF:
                startYkfListQuery();
                break;
            case DREAM:
                startDreamListQuery();
                break;
        }
    }

    /**
     * 安栄の通信処理開始
     */
    private void startAnneiListQuery() {
        mCompositeSubscription.add(
                AnneiStatusListApi.request(URL_ANNEI_LIST)
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
                                saveResultToCache(result);
                            }
                        })
                );
    }

    /**
     * 通信した結果をキャッシュに保存
     * 
     * @param result 通信値
     */
    private void saveResultToCache(Result result) {
        CacheManager.getInstance().saveNowTimeStamp(getParam());
        CacheManager.getInstance().putResult(getParam(), result);
    }

    /**
     * 八重山観光フェリーAPIを呼び出す
     */
    private void startYkfListQuery() {

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
                                saveResultToCache(result);
                            }
                        })
                );
    }

    /**
     * ドリームAPIを呼び出す
     */
    private void startDreamListQuery() {

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
                                saveResultToCache(result);
                            }
                        })
                );
    }

    /**
     * 更新時間
     *
     * @param update
     */
    private void setUpdate(String update) {
        if (TextUtils.isEmpty(update)) {
            mUpdateText.setVisibility(View.GONE);
            return;
        }
        mUpdateText.setText(update);

    }

    /**
     * タイトル
     *
     * @param title
     */
    private void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTitleText.setVisibility(View.GONE);
            return;
        }

        String replaceTitle = StringUtils.replacePunctuation(StringUtils.replaceSpaceJ(title));
        mTitleText.setText(replaceTitle);
    }

    public void finishQuery() {
        mHeaderView.setVisibility(View.VISIBLE);
        mProgressWheel.setVisibility(View.GONE);
    }

    private void onResultListQuery(Result result) {
        if (result == null || result.getLiners().isEmpty() || result.getLiners().size() == 0) {
            failedQuery();
            return;
        }
        mHeaderView.setVisibility(View.VISIBLE);
        setTitle(result.getTitle());
        setUpdate(result.getUpdateTime());

        mListAdapter.clear();
        mListAdapter.addAll(result.getLiners());
    }

    public void failedQuery() {
        Crashlytics.logException(new Exception("Status List Api Failed"));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Company company = (Company) getArguments().get(StatusListTabFragment.class.getCanonicalName());
        if (company == null) {
            return;
        }

        Liner liner = (Liner) getListAdapter().getItem(position - 1);
        liner.setCompany(company);
        switch (company) {
            case ANNEI:
                startStatusDetailActivity(liner);
                break;
            case YKF:
                startStatusDetailYkfActivity(liner);
                break;
            case DREAM:
                startStatusDetailDreamActivity(liner);
                break;
            default:
                break;
        }
    }

    /**
     * 安栄の詳細画面に遷移
     *
     * @param liner 運航状況
     */
    private void startStatusDetailActivity(Liner liner) {
        Intent intent = new Intent(getActivity(), StatusDetailAnneiActivity.class);
        intent.putExtra(StatusDetailAnneiActivity.class.getName(), liner);
        startActivity(intent);
    }

    /**
     * 八重山観光フェリーの詳細に遷移
     *
     * @param liner
     */
    private void startStatusDetailYkfActivity(Liner liner) {
        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
        ykfLinerDetail.setLiner(liner);
        ykfLinerDetail.setPort(liner.getPort());

        Intent intent = new Intent(getActivity(), StatusDetailYkfActivity.class);
        intent.putExtra(StatusDetailYkfActivity.class.getName(), ykfLinerDetail);
        startActivity(intent);
    }

    /**
     * ドリーム観光の詳細に遷移
     *
     * @param liner
     */
    private void startStatusDetailDreamActivity(Liner liner) {
        YkfLinerDetail ykfLinerDetail = new YkfLinerDetail();
        ykfLinerDetail.setLiner(liner);
        ykfLinerDetail.setPort(liner.getPort());

        Intent intent = new Intent(getActivity(), StatusDetailDreamActivity.class);
        intent.putExtra(StatusDetailDreamActivity.class.getName(), ykfLinerDetail);
        startActivity(intent);
    }
}
