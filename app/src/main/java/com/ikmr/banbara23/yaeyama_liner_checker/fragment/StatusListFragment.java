
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;
import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;

/**
 * ステータスリストのFragment
 */
public class StatusListFragment extends ListFragment implements ListFragmentInterface {
    StatusListAdapter mListAdapter;
    ListView mListView;
    TextView mTitleText;
    TextView mUpdateText;
    FrameLayout mHeaderCardLayout;
    // ProgressWheel mProgressWheel;

    final static String PARAM_COMPANY = "company";

    public StatusListFragment() {
    }

    public static StatusListFragment NewInstance(Company company) {
        StatusListFragment fragment = new StatusListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_COMPANY, company);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        // mProgressWheel = (ProgressWheel)
        // view.findViewById(R.id.fragment_list_material_progress_bar);
        mHeaderCardLayout = (CardView) view.findViewById(R.id.fragment_status_list_header_card_view);
        mTitleText = (TextView) view.findViewById(R.id.fragment_status_list_toolbar_title_text);
        mUpdateText = (TextView) view.findViewById(R.id.fragment_status_list_toolbar_update_text);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null && activity instanceof QueryInterface) {
            // リストアダプターの生成
            mListAdapter = new StatusListAdapter(getActivity().getApplicationContext(), getActivity());
            setListAdapter(mListAdapter);
            // API通信処理の開始準備の完了
            ((QueryInterface) activity).startQuery();
            showProgress();
        }
    }

    /**
     * 読込中の表示開始
     */
    private void showProgress() {
        mHeaderCardLayout.setVisibility(View.GONE);
        // mProgressWheel.setVisibility(View.VISIBLE);
    }

    /**
     * 読込中の表示完了
     */
    private void hideProgress() {
        mHeaderCardLayout.setVisibility(View.VISIBLE);
        // mProgressWheel.setVisibility(View.GONE);
    }

    /**
     * 初回検索時
     */
    @Override
    public void onResetQuery() {
        // 処理なし
    }

    /**
     * 検索開始時
     */
    @Override
    public void onStartQuery() {
        showProgress();
        mListAdapter.clear();
        mListAdapter.notifyDataSetChanged();
    }

    /**
     * API結果取得時
     */
    @Override
    public void onResultQuery(Result result) {
        bind(result);
    }

    private void bind(Result result) {
        mListAdapter.clear();
        if (result == null) {
            return;
        }
        mListAdapter.addAll(result.getLiners());

        // ヘッダー設定
        setTitle(result.getTitle());
        setUpdate(result.getUpdateTime());

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
        }

        String replaceTitle = StringUtils.replacePunctuation(StringUtils.replaceSpaceJ(title));
        mTitleText.setText(replaceTitle);
    }

    /**
     * APIエラー時
     */
    @Override
    public void onFailedQuery() {
        // mProgressWheel.setVisibility(View.GONE);
        Toast.makeText(getActivity().getApplicationContext(), "エラーが発生しました", Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * API 終了時
     */
    @Override
    public void onFinishQuery() {
        hideProgress();
    }
}
