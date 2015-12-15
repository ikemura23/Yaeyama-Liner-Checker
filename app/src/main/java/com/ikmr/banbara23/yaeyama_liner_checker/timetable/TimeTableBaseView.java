
package com.ikmr.banbara23.yaeyama_liner_checker.timetable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 時刻表の継承元View
 */
public class TimeTableBaseView extends LinearLayout {
    public TimeTableBaseView(Context context) {
        super(context);
    }

    public TimeTableBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void switchViews(Company company) {

    }

    /**
     * 子ビューの時刻表を観光会社別に一斉切り替え
     * 
     * @param company 観光会社
     * @param aneiViews 安栄の時刻表の行レイアウト
     * @param ykfViews 八重山観光フェリーの時刻表の行レイアウト
     */
    public void switcCompany(Company company, List<View> aneiViews, List<View> ykfViews) {
        if (company == null) {
            return;
        }
        if (aneiViews == null || aneiViews.isEmpty()) {
            return;
        }
        if (ykfViews == null || ykfViews.isEmpty()) {
            return;
        }
        if (company == Company.ANNEI) {
            ButterKnife.apply(aneiViews, ENABLED, false);
            ButterKnife.apply(ykfViews, DISABLE);
        }
        else {
            ButterKnife.apply(aneiViews, DISABLE);
            ButterKnife.apply(ykfViews, ENABLED, false);
        }
    }

    /***
     * 非表示一括切り替え用
     */
    protected static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(GONE);
        }
    };

    /***
     * 表示一括切り替え用
     */
    protected static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            view.setVisibility(VISIBLE);
        }
    };
}
