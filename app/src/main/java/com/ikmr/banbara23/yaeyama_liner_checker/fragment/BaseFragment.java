
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.app.Fragment;
import android.content.Context;

/**
 * Created by banbara23 on 15/09/19.
 */
public class BaseFragment extends Fragment {
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
