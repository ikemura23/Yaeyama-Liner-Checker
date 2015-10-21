
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    ProgressBar mProgressBar;
    ListView mListView;
    TextView mTitleText;
    TextView mUpdateText;
    LinearLayout mHeaderLayout;
    FrameLayout mProgressLayout;
    AdView mAdView;

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
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBar.setIndeterminate(true);
        mHeaderLayout = (LinearLayout) view.findViewById(R.id.fragment_status_list_header);
        mTitleText = (TextView) view.findViewById(R.id.fragment_status_list_toolbar_title_text);
        mUpdateText = (TextView) view.findViewById(R.id.fragment_status_list_toolbar_update_text);
        mProgressLayout = (FrameLayout) view.findViewById(R.id.progressbar_layout);
        mAdView = (AdView) view.findViewById(R.id.adView);
        return view;
    }

    /**
     * 広告読み込み
     */
    protected void loadAd() {
        if (mAdView == null) {
            return;
        }
        try {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } catch (Exception e) {
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadAd();
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
        mHeaderLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
        // mProgressBar.setVisibility(View.GONE);
    }

    /**
     * 読込中の表示完了
     */
    private void hideProgress() {
        mHeaderLayout.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.INVISIBLE);
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
        mProgressBar.setVisibility(View.GONE);
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
