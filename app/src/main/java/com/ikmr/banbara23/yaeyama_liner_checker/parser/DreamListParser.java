
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.StatusListResult;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 安栄HTMLのパース処理
 */
public class DreamListParser {

    public static StatusListResult pars(Document doc) {

        StatusListResult statusListResult = new StatusListResult();
        // <div id="liner"> 取得
        Elements text = doc.getElementsByClass("text");
        if (text == null) {
            return null;
        }
        if (text.size() == 0) {
            return null;
        }

        return statusListResult;
    }

    /**
     * 更新日時の取得
     *
     * @param text クラス
     * @return 更新日時
     */
    private static String getValue(Elements text) {
        StringBuilder sb = new StringBuilder();
        if (text == null) {
            return "";
        }
        for (Element element : text.get(0).children()) {
            // String elementTxt = element.text();
            // if
            // (StringUtils.isNotEmpty(StringUtils.replaceAllSpace(element.text())))
            // {
            // sb.append(elementTxt);
            // }
            sb.append(element.text());
            sb.append("\n");
        }
        return sb.toString();
    }
}
