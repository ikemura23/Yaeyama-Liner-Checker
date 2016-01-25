
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.fragment.StatusListTabFragment;

/**
 * PagerAdapter
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Company company;
        switch (position) {
            case 0:
                company = Company.ANNEI;
                break;
            case 1:
                company = Company.YKF;
                break;
            case 2:
                company = Company.DREAM;
                break;
            default:
                company = null;
        }
        return StatusListTabFragment.NewInstance(company);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
