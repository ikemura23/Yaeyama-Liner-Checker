
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Fragment;
import android.content.Context;

public class BaseFragment extends Fragment {
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
