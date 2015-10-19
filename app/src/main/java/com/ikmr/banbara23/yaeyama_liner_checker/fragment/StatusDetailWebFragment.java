
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

/**
 * 詳細のフラグメント
 */
public class StatusDetailWebFragment extends BaseFragment {

    StatusDetailView mStatusDetailView;
//    ProgressBar mProgressBar;

    public static StatusDetailWebFragment NewInstance(YkfLinerDetail ykfLinerDetail) {
        StatusDetailWebFragment fragment = new StatusDetailWebFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailWebFragment.class.getName(), ykfLinerDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private YkfLinerDetail getParam() {
        return getArguments().getParcelable(StatusDetailWebFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_web, container, false);
        mStatusDetailView = (StatusDetailView) view.findViewById(R.id.fragment_status_detail_view);
//        mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_web_progressbar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mStatusDetailView.bind(getParam().getLiner(), createValueText());
    }

    private String createValueText() {
        if (getParam() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(getParam().getUpdateTime())) {
            sb.append(getParam().getUpdateTime());
            sb.append("\n");
        }
        if (StringUtils.isNotEmpty(getParam().getTitle())) {
            sb.append(getParam().getTitle());
            sb.append("\n");
        }
        if (StringUtils.isNotEmpty(getParam().getLiner().getText())) {
            sb.append(getParam().getLiner().getText());
        }
        return sb.toString();
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        Activity activity = getActivity();
//        if (activity != null && activity instanceof QueryInterface) {
//            // API通信処理の開始準備の完了
//            ((QueryInterface) activity).startQuery();
//            showProgress();
//        }
//    }

//    /**
//     * 読込中の表示開始
//     */
//    private void showProgress() {
//        mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onResetQuery() {
//
//    }
//
//    @Override
//    public void onStartQuery() {
//
//    }
//
//    @Override
//    public void onResultQuery(Liner liner, String value) {
//        mStatusDetailView.bind(getParam(), value);
//    }
//
//    @Override
//    public void onFailedQuery() {
//
//    }
//
//    @Override
//    public void onFinishQuery() {
//        mProgressBar.setVisibility(View.GONE);
//    }
}
