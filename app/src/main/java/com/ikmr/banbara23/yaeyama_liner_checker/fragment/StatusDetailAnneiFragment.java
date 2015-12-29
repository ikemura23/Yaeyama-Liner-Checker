
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ikmr.banbara23.yaeyama_liner_checker.AnneiStatusListApi;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.annei.AnneiTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailTextView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailTopView;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 詳細のフラグメント
 */
public class StatusDetailAnneiFragment extends BaseFragment implements FragmentInterface {

    // ButterKnife Bind --------------------------------------------
    @Bind(R.id.fragment_status_detail_text_view)
    StatusDetailTextView mStatusDetailTextView;

    @Bind(R.id.fragment_time_table_annei_view)
    AnneiTimeTableView mAnneiTimeTableView;

    @Bind(R.id.fragment_status_detail_progressbar)
    ProgressWheel mProgressBar;

    @Bind(R.id.fragment_status_detail_reload_button)
    Button mFragmentStatusDetailErrorButton;

    @Bind(R.id.fragment_status_detail_top_view)
    StatusDetailTopView mStatusDetailTopView;

    @OnClick(R.id.fragment_status_detail_reload_button)
    void reloadButtonClick(View view) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

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
        getAnneiList();
        getAnneiDetail();
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private Liner getParam() {
        return getArguments().getParcelable(StatusDetailAnneiFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_annei, container, false);
        // mProgressWheel = (ProgressWheel)
        // view.findViewById(R.id.fragment_detail_material_progress_bar);

        ButterKnife.bind(this, view);

        // 電話ボタン
        view.findViewById(R.id.view_action_box_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTel();
            }
        });
        // サイト
        view.findViewById(R.id.view_action_box_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWeb();
            }
        });
        return view;
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
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
        }
    }

    /**
     * 安栄のTOPの一覧を取得
     */
    private void getAnneiDetail() {

    }

    /**
     * 安栄の港の詳細を取得
     */
    private void getAnneiList() {
        mCompositeSubscription.add(
                AnneiStatusListApi.request()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<Result>() {
                            @Override
                            public void onCompleted() {
                                // 完了
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 失敗
                                Log.d("StatusDetailAnneiFragme", "失敗");
                            }

                            @Override
                            public void onNext(Result result) {
                                Log.d("StatusDetailAnneiFragme", "result:" + result);
                            }
                        })
                );
    }

    @Override
    public void onStartQuery(Port port) {
        mFragmentStatusDetailErrorButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mStatusDetailTopView.setVisibility(View.GONE);
        mAnneiTimeTableView.setVisibility(View.VISIBLE);
        mAnneiTimeTableView.switchPortView(port);
    }

    @Override
    public void onResultQuery(Liner liner, String value) {
        mStatusDetailTopView.setVisibility(View.VISIBLE);
        mStatusDetailTopView.bind(liner);

        mStatusDetailTextView.setVisibility(View.VISIBLE);
        mStatusDetailTextView.bind(value);
    }

    @Override
    public void onFailedQuery() {
        mProgressBar.setVisibility(View.GONE);
        mFragmentStatusDetailErrorButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishQuery() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void startTel() {
        String tell;
        switch (getParam().getCompany()) {
            case ANNEI:
                tell = getActivity().getApplicationContext().getString(R.string.tel_annei);
                break;

            case YKF:
                tell = getActivity().getApplicationContext().getString(R.string.tel_ykf);
                break;
            default:
                tell = null;
        }
        if (tell == null) {
            return;
        }
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("tel:" + tell));

            startActivity(intent);
        } catch (Exception e) {
            // 何もしない
        }
    }

    private void startWeb() {
        String hpUrl;
        switch (getParam().getCompany()) {
            case ANNEI:
                hpUrl = getAneiPortUrl();
                break;

            case YKF:
                hpUrl = getActivity().getApplicationContext().getString(R.string.hp_ykf);
                break;
            default:
                hpUrl = getActivity().getApplicationContext().getString(R.string.hp_annei);
        }
        if (hpUrl == null) {
            return;
        }

        Uri uri = Uri.parse(hpUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 何もしない
        }

    }

    /***
     * 安栄の港別のURLを返す
     *
     * @return URL
     */
    private String getAneiPortUrl() {
        switch (getParam().getPort()) {
            case TAKETOMI:
                return getActivity().getApplicationContext().getString(R.string.url_annei_taketomi);
            case KOHAMA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_kohama);
            case OOHARA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_oohara);
            case UEHARA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_uehara);
            case KUROSHIMA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_kuroshima);
            case HATOMA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_hatoma);
            case HATERUMA:
                return getActivity().getApplicationContext().getString(R.string.url_annei_hateruma);
            default:
                return getActivity().getApplicationContext().getString(R.string.hp_annei);
        }
    }
}
