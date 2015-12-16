
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.StatusListResult;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * 安栄HTMLのパース処理
 */
public class DreamListParser {

    public static StatusListResult pars(Document doc) {

        StatusListResult statusListResult = new StatusListResult();
        // <div id="liner"> 取得
        Elements tables = doc.getElementsByTag("table");
        if (isEmptyElements(tables)) {
            return null;
        }

        // ６番目のテーブルが運航状況
        Element table6 = tables.get(6);
        Elements tr = table6.getElementsByTag("tr");
        if (isEmptyElements(tr)) {
            return null;
        }

        HashMap<Port, Liner> mLiners = new HashMap<>();

        for (Element tag : tr) {
            Elements td = tag.getElementsByTag("td");
            if (isEmptyElements(td)) {
                continue;
            }
            // tdタグが１つならテーブルのタイトル行、２つなら港別の運航状況
            if (td.size() == 1) {
                statusListResult.setUpdateTime(getUpdateTime(td.text()));
            }
            else {
                Port port = getPort(td.get(0));
                Liner liner = createLiner(td.get(1), port);
                mLiners.put(port, liner);
            }
        }
        statusListResult.setLiners(mLiners);

        return statusListResult;
    }

    /**
     * 文字からPortを返す
     * 
     * @param td タグ
     * @return 港
     */
    private static Port getPort(Element td) {
        if (td == null) {
            return null;
        }
        return ParseUtil.getStrContainPort(td.text());
    }

    /**
     * 運航状況を返す
     *
     * @param td タグ
     * @return 運航状況
     */
    private static Liner createLiner(Element td, Port port) {
        if (td == null) {
            return null;
        }
        Liner liner = new Liner();
        liner.setPort(port);
        liner.setText(td.text());
        liner.setStatus(getStatus(td.text()));

        return liner;
    }

    /**
     * 文字からステータスを判定して返す
     * 
     * @param text 運航状況の文字
     * @return 運航状況ステータス
     */
    private static Status getStatus(String text) {
        if (text.equals("通常運航")) {
            return Status.NORMAL;
        }
        else if (text.equals("欠航")) {
            return Status.CANCEL;
        }
        else {
            return Status.CAUTION;
        }
    }

    /**
     * Elementsの空チェック
     * 
     * @param elements パースされたタグ値
     * @return true:空 false:値あり
     */
    private static boolean isEmptyElements(Elements elements) {
        return elements == null || elements.size() == 0;
    }

    /**
     * 更新時刻を取得<br>
     * 本日の運航状況　2015年12月16日（水）07:00更新 という値で取れる
     * 
     * @param text 編集前の更新時刻
     * @return 更新時刻
     */
    private static String getUpdateTime(String text) {
        return text.replace("本日の運航状況　", "");
    }

    // /**
    // * 更新日時の取得
    // *
    // * @param text クラス
    // * @return 更新日時
    // */
    // private static String getValue(Elements text) {
    // StringBuilder sb = new StringBuilder();
    // if (text == null) {
    // return "";
    // }
    // for (Element element : text.get(0).children()) {
    // // String elementTxt = element.text();
    // // if
    // // (StringUtils.isNotEmpty(StringUtils.replaceAllSpace(element.text())))
    // // {
    // // sb.append(elementTxt);
    // // }
    // sb.append(element.text());
    // sb.append("\n");
    // }
    // return sb.toString();
    // }
}
