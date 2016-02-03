
package com.ikmr.banbara23.yaeyama_liner_checker.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ikmr.banbara23.yaeyama_liner_checker.R;

/**
 * PreferenceFragment継承
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
