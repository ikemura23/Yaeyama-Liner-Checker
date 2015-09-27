
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.text.TextUtils;

/**
 * Created by banbara23 on 15/09/27.
 */
public class StringUtils {
    /**
     * 空白か
     * 
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    /**
     * 空白ではないか
     * 
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !TextUtils.isEmpty(value);
    }

    /**
     * 空白を置き換えて消す
     * 
     * @param value
     * @return
     */
    public static String replaceAllSpace(String value) {
        if (isEmpty(value)) {
            return value;
        }
        String replace = value.replaceAll(" ", "");
        replace = replace.replaceAll("　", "");
        return replace;
    }
}
