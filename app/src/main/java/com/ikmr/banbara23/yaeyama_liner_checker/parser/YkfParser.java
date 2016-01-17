
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
 * 八重山観光フェリーのHTMLパースクラス
 */
public class YkfParser {

    public static Result pars(Document doc) {
        if (doc == null) {
            return null;
        }
        Result result = new Result();
        ArrayList<Liner> mLiners = new ArrayList<>();

        // タイトル（通常はないがたまに出てくる）
        result.setTitle(getTitle(doc.getElementsByTag("body")));
        // 更新日時 「Ｈ27/9/26 06：30現在」という感じになる
        result.setUpdateTime(getUpdateTime(doc.getElementsByTag("body")));

        // <div id="liner"> 取得
        Elements tr = doc.getElementsByTag("tr");

        if (tr == null) {
            return null;
        }
        ArrayList<Port> array = getAnneiPortArray();
        for (Port port : array) {
            mLiners.add(getDivPort(port, tr));
        }
        result.setLiners(mLiners);
        return result;
    }

    /**
     * 更新日時の取得
     *
     * @param body bodyタグ
     * @return 更新日時 [Ｈ27/9/26 06：30現在]
     */
    private static String getUpdateTime(Elements body) {
        if (body == null) {
            return "";
        }
        Element b = body.get(0);
        if (b == null) {
            return null;
        }
        Elements p = b.getElementsByTag("p");
        if (p == null || p.size() < 2) {
            return null;
        }

        return p.get(1).text();
    }

    /**
     * タイトル取得
     *
     * @param body タグ
     * @return タイトル
     */
    private static String getTitle(Elements body) {
        if (body == null) {
            return "";
        }
        Element b = body.get(0);
        if (b == null) {
            return null;
        }
        Elements p = b.getElementsByTag("p");
        if (p == null || p.size() < 3) {
            return null;
        }

        return p.get(2).text();
    }

    /**
     * 波照間
     *
     * @param port 港名
     * @param tr trタグ
     * @return
     */
    private static Liner getDivPort(Port port, Elements tr) {
        Liner liner = new Liner();
        liner.setPort(port);
        for (int i = 1; i < tr.size(); i++) {
            Element element = tr.get(i);
            if (element == null || element.children().size() < 1) {
                continue;
            }
            if (element.child(0).text().contains(port.getPortSimple())) {
                liner.setStatus(getStatus(element.child(1).text()));
                liner.setText(element.child(2).text());
                return liner;
            }
        }
        return liner;
    }

    /**
     * ステータス判別
     * 
     * @param text
     * @return
     */
    private static Status getStatus(String text) {

        if (text.contains("○") || text.contains("〇")) {
            return Status.NORMAL;
        }
        else if (text.contains("×")) {
            return Status.CANCEL;
        }
        else {
            return Status.CAUTION;
        }
    }

    private static ArrayList<Port> getAnneiPortArray() {
        ArrayList<Port> list = new ArrayList<>();
        list.add(Port.TAKETOMI);
        list.add(Port.KOHAMA);
        list.add(Port.KUROSHIMA);
        list.add(Port.OOHARA);
        list.add(Port.UEHARA);
        list.add(Port.HATOMA);
        return list;
    }
}
