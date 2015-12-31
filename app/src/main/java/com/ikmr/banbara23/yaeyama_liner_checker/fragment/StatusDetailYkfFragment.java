
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
import android.widget.ProgressBar;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.api.AnneiStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailTextView;

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
public class StatusDetailYkfFragment extends BaseFragment {

    @Bind(R.id.fragment_ykf_status_detail_view)
    StatusDetailTextView mStatusDetailTextView;
    @Bind(R.id.fragment_time_table_view)
    YkfTimeTableView mYkfTimeTableView;
    @Bind(R.id.fragment_status_detail_content_layout)
    LinearLayout mFragmentStatusDetailContentLayout;
    @Bind(R.id.fragment_status_detail_progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.fragment_status_detail_reload_button)
    Button mReloadButton;

    @OnClick(R.id.fragment_status_detail_reload_button)
    void reloadClick(View view) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    @OnClick(R.id.view_action_box_tel)
    void tellClick(View view) {
        startTell();
    }

    @OnClick(R.id.view_action_box_web)
    void webClick(View view) {
        startWeb();
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
        mStatusDetailTextView = (StatusDetailTextView) view.findViewById(R.id.fragment_ykf_status_detail_view);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startQuery();
        // mFragmentStatusDetailContentLayout.setVisibility(View.VISIBLE);
        // mYkfTimeTableView.switchPortView(getParam().getPort());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mCompositeSubscription.unsubscribe();
    }

    // /**
    // * コメント作成
    // *
    // * @return
    // */
    // private String createValueText() {
    // if (getParam() == null) {
    // return "";
    // }
    // StringBuilder sb = new StringBuilder();
    // // if (StringUtils.isNotEmpty(getParam().getUpdateTime())) {
    // // sb.append(getParam().getUpdateTime());
    // // sb.append("\n");
    // // }
    // if (StringUtils.isNotEmpty(getParam().getTitle())) {
    // sb.append(getParam().getTitle());
    // sb.append("\n");
    // }
    // if (StringUtils.isNotEmpty(getParam().getLiner().getText())) {
    // sb.append(getParam().getLiner().getText());
    // }
    // return sb.toString();
    // }

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
    }

    /**
     * 八重山観光フェリーAPIを呼び出す
     */
    private void getYkfList() {

        mCompositeSubscription.add(
                AnneiStatusListApi.request(URL_YKF_LIST)
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
        // TODO: 15/12/31 ステータスを反映
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
        mFragmentStatusDetailContentLayout.setVisibility(View.VISIBLE);
        mYkfTimeTableView.switchPortView(getParam().getPort());
    }
}
