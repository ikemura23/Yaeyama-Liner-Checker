
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.dream.DreamTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ドリーム観光の詳細画面フラグメント
 */
public class StatusDetailDreamFragment extends BaseFragment {

    @Bind(R.id.fragment_dream_status_detail_view)
    StatusDetailView mStatusDetailView;
    @Bind(R.id.fragment_dream_time_table_view)
    DreamTimeTableView mDreamTimeTableView;
    @Bind(R.id.fragment_dream_status_detail_content_layout)
    LinearLayout mFragmentDreamStatusDetailContentLayout;

    @OnClick(R.id.view_action_box_tel)
    void tellClick(View view) {
        startTell();
    }

    @OnClick(R.id.view_action_box_web)
    void webClick(View view) {
        startWeb();
    }

    public static StatusDetailDreamFragment NewInstance(YkfLinerDetail ykfLinerDetail) {
        StatusDetailDreamFragment fragment = new StatusDetailDreamFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StatusDetailDreamFragment.class.getName(), ykfLinerDetail);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private YkfLinerDetail getParam() {
        return getArguments().getParcelable(StatusDetailDreamFragment.class.getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail_dream, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentDreamStatusDetailContentLayout.setVisibility(View.VISIBLE);
        mStatusDetailView.bind(getParam().getLiner(), createValueText());
        mDreamTimeTableView.switchPortView(getParam().getPort());
    }

    private String createValueText() {
        if (getParam() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // if (StringUtils.isNotEmpty(getParam().getUpdateTime())) {
        // sb.append(getParam().getUpdateTime());
        // sb.append("\n");
        // }
        if (StringUtils.isNotEmpty(getParam().getTitle())) {
            sb.append(getParam().getTitle());
            sb.append("\n");
        }
        if (StringUtils.isNotEmpty(getParam().getLiner().getText())) {
            sb.append(getParam().getLiner().getText());
        }
        return sb.toString();
    }

    private void startTell() {
        String tell;
        switch (getParam().getLiner().getCompany()) {
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
            Log.d("StatusDetailYkfFragment", "e:" + e.getMessage());
            // 何もしない
        }
    }

    private void startWeb() {
        String hpUrl;
        switch (getParam().getLiner().getCompany()) {
            case ANNEI:
                hpUrl = getActivity().getApplicationContext().getString(R.string.hp_annei);
                break;

            case YKF:
                hpUrl = getActivity().getApplicationContext().getString(R.string.hp_ykf);
                break;
            default:
                hpUrl = null;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
