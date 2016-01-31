
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.content.Context;
import android.support.v4.app.ListFragment;

public class BaseListFragment extends ListFragment {
    public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
