
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
public class StatusDetailYkfFragment extends BaseFragment {

    StatusDetailView mStatusDetailView;

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
        mStatusDetailView = (StatusDetailView) view.findViewById(R.id.fragment_ykf_status_detail_view);

        // 電話ボタン
        view.findViewById(R.id.view_action_box_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTell();
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
    public void onResume() {
        super.onResume();
        mStatusDetailView.bind(getParam().getLiner(), createValueText());
    }

    private String createValueText() {
        if (getParam() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
//        if (StringUtils.isNotEmpty(getParam().getUpdateTime())) {
//            sb.append(getParam().getUpdateTime());
//            sb.append("\n");
//        }
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
}
