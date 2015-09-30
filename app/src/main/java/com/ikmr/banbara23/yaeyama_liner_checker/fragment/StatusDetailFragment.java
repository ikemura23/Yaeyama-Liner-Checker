
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.view.StatusDetailView;

/**
 * Created by banbara23 on 15/09/19.
 */
public class StatusDetailFragment extends BaseFragment {

    StatusDetailView mStatusDetailView;

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
        Log.d("StatusDetailFragment", "getArguments():" + getArguments());
        return getArguments().getParcelable(StatusDetailFragment.class.getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity().getApplicationContext(), getParam().toString(), Toast.LENGTH_SHORT)
                .show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_detail, container, false);
        mStatusDetailView = (StatusDetailView) view.findViewById(R.id.fragment_status_detail);
        return view;
    }
}
