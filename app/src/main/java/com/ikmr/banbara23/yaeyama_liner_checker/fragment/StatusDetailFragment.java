
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

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

/**
 * 詳細のフラグメント
 */
public class StatusDetailFragment extends BaseFragment implements FragmentInterface {

    StatusDetailView mStatusDetailView;

    // ProgressWheel mProgressWheel;

    public static StatusDetailFragment NewInstance(Liner liner) {
        StatusDetailFragment fragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailFragment.class.getName(), liner);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private Liner getParam() {
        return getArguments().getParcelable(StatusDetailFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail, container, false);
        mStatusDetailView = (StatusDetailView) view.findViewById(R.id.fragment_status_detail_view);
        // mProgressWheel = (ProgressWheel)
        // view.findViewById(R.id.fragment_detail_material_progress_bar);

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
            showProgress();
        }
    }

    /**
     * 読込中の表示開始
     */
    private void showProgress() {
        // mProgressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResetQuery() {

    }

    @Override
    public void onStartQuery() {

    }

    @Override
    public void onResultQuery(Liner liner, String value) {
        mStatusDetailView.setVisibility(View.VISIBLE);
        mStatusDetailView.bind(liner, value);
    }

    @Override
    public void onFailedQuery() {

    }

    @Override
    public void onFinishQuery() {
        // mProgressWheel.setVisibility(View.GONE);
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
            Log.d("StatusDetailFragment", e.getMessage());
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
