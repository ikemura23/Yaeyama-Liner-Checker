
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 安栄HTMLのパース処理
 */
public class DreamListParser {

    public static Result pars(Document doc) {

        Result result = new Result();
        // StatusListResult statusListResult = new StatusListResult();
        // <div id="liner"> 取得
        if (doc == null) {
            return null;
        }
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
        result.setUpdateTime(getUpdateTime(tr));

        // HashMap<Port, Liner> mLiners = new HashMap<>();
        ArrayList<Liner> mLiners = new ArrayList<>();

        ArrayList<Port> array = getPortArray();
        for (Port port : array)
            mLiners.add(getDivPort(port, tr));

        result.setLiners(mLiners);

        return result;
    }

    /**
     * 港を元に運航状況を作る
     * 
     * @param port
     * @param tr
     * @return
     */
    private static Liner getDivPort(Port port, Elements tr) {
        Liner liner = new Liner();
        liner.setPort(port);
        for (Element tag : tr) {
            Elements td = tag.getElementsByTag("td");
            if (isEmptyElements(td)) {
                continue;
            }
            // tdタグが１つならテーブルのタイトル行、２つなら港別の運航状況
            if (td.size() == 1) {
                continue;
            }
            String portName = td.get(0).text();
            String comment = td.get(1).text();
            if (portName.contains(port.getPortSimple())) {
                liner.setText(comment);
                liner.setStatus(getStatus(comment));
            }
        }

        return liner;
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
        else if (text.equals("運休日")) {
            return Status.SUSPEND;
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
     * @param tr trタグ
     * @return 更新時刻
     */
    private static String getUpdateTime(Elements tr) {
        String updateTime = null;
        for (Element tag : tr) {
            Elements td = tag.getElementsByTag("td");
            if (isEmptyElements(td)) {
                continue;
            }
            // tdタグが１つならテーブルのタイトル行、２つなら港別の運航状況
            if (td.size() == 1) {
                updateTime = td.text().replace("本日の運航状況　", "");
            }
        }
        return updateTime;
    }

    private static ArrayList<Port> getPortArray() {
        ArrayList<Port> list = new ArrayList<>();
        list.add(Port.TAKETOMI);
        list.add(Port.KOHAMA);
        list.add(Port.KUROSHIMA);
        list.add(Port.OOHARA);
        list.add(Port.HATOMA_UEHARA);
        list.add(Port.PREMIUM_DREAM);
        list.add(Port.SUPER_DREAM);
        return list;
    }
}
