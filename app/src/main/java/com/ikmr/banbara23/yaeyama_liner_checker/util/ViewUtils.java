
package com.ikmr.banbara23.yaeyama_liner_checker.util;

import android.view.View;

import java.util.List;

/**
 * Created by banbara23 on 15/12/14.
 */
public class ViewUtils {

    static public void setVisivilityViews(List<View> views, int visibility) {
        for (View view : views) {
            view.setVisibility(visibility);
        }
    }


}
