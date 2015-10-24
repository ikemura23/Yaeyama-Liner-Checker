
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * 詳細のフラグメント
 */
public class StatusDetailFragment extends BaseFragment implements FragmentInterface {

    StatusDetailView mStatusDetailView;
    ProgressWheel mProgressWheel;

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
        mProgressWheel = (ProgressWheel) view.findViewById(R.id.fragment_detail_material_progress_bar);

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
        mProgressWheel.setVisibility(View.VISIBLE);
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
        mProgressWheel.setVisibility(View.GONE);
    }

    private void startTel() {

    }

    private void startWeb() {

    }
}
