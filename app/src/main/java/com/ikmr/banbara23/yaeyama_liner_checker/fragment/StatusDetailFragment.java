
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

/**
 * Created by banbara23 on 15/09/19.
 */
public class StatusDetailFragment extends BaseFragment {

    StatusDetailView mStatusDetailView;

    public static StatusDetailFragment NewInstance(String value) {
        StatusDetailFragment fragment = new StatusDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StatusDetailFragment.class.getName(), value);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * パラメータ取得
     *
     * @return
     */
    private String getParam() {
        return getArguments().getString(StatusDetailFragment.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity().getApplicationContext(), getParam(), Toast.LENGTH_SHORT)
                .show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail, container, false);
        // mStatusDetailView = (StatusDetailView)
        // view.findViewById(R.id.fragment_detail_view);
        return view;
    }
}