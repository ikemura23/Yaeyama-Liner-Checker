
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ikmr.banbara23.yaeyama_liner_checker.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.ListFragmentInterface;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.StatusListAdapter;

/**
 * ステータスリストのFragment
 */
public class StatusListFragment extends ListFragment implements ListFragmentInterface {
    StatusListAdapter mListAdapter;
    ProgressBar mProgressBar;
    ListView mListView;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new StatusListAdapter(getActivity().getApplicationContext(), getActivity());
        setListAdapter(mListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getHtml();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        return view;
    }

    public void getHtml() {
        mListAdapter.clear();
        // 通信して取得

        mListAdapter.addAll(getData());
        mListView.setVisibility(View.VISIBLE);
    }

    /**
     * 結果を作成
     *
     * @return
     */
    private List<String> getData() {
        List<String> list = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    /** 初回検索時 */
    @Override
    public void onResetQuery(String newVersion) {
        mProgressBar.setIndeterminate(true);
    }

    /** 検索開始時 */
    @Override
    public void onStartQuery(String version) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /** API結果取得時 */
    @Override
    public void onResultQuery(String version, int count, int total) {

    }

    /** APIエラー時 */
    @Override
    public void onFailedQuery(Exception error) {

    }

    /** API 終了時 */
    @Override
    public void onFinishQuery() {

    }
}
