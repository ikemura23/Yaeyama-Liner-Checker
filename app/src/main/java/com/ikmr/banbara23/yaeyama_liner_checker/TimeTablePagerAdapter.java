
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.timetable.TimeTableFragment;

/**
 * PagerAdapter
 */
public class TimeTablePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TimeTablePagerAdapter(FragmentManager fm, int NumOfTabs) {
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
        return TimeTableFragment.NewInstance(company);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public Fragment findFragmentByPosition(ViewPager viewPager, int position) {
        return (Fragment) instantiateItem(viewPager, position);
    }
}
