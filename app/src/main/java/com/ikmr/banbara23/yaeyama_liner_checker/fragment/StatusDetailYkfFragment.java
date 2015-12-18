
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

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
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.YkfLinerDetail;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.ykf.YkfTimeTableView;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 詳細のフラグメント
 */
public class StatusDetailYkfFragment extends BaseFragment {

    @Bind(R.id.fragment_ykf_status_detail_view)
    StatusDetailView mStatusDetailView;
    @Bind(R.id.fragment_time_table_view)
    YkfTimeTableView mYkfTimeTableView;
    @Bind(R.id.fragment_status_detail_content_layout)
    LinearLayout mFragmentStatusDetailContentLayout;
    @Bind(R.id.view_action_box_tel)
    Button mViewActionBoxTel;
    @Bind(R.id.view_action_box_web)
    Button mViewActionBoxWeb;

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
        ButterKnife.bind(this, view);

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
        mFragmentStatusDetailContentLayout.setVisibility(View.VISIBLE);
        mStatusDetailView.bind(getParam().getLiner(), createValueText());
        mYkfTimeTableView.switchPortView(getParam().getPort());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
}
