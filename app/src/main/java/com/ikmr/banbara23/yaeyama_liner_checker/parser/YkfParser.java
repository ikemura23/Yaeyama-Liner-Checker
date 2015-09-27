
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import android.util.Log;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by banbara23 on 15/09/26.
 */
public class YkfParser {

    public static Result pars(Document doc) {
        if (doc == null) {
            return null;
        }
        Result result = new Result();
        ArrayList<Liner> mLiners = new ArrayList<>();

        // 八重山観光フェリーはタイトルなし
        result.setTitle(null);
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
        Log.d("YkfParser", p.get(1).text());

        return p.get(1).text();
    }

    // /**
    // * タイトル取得
    // *
    // * @param h5 タグ
    // * @return タイトル
    // */
    // private static String getTitle(Elements h5) {
    // if (h5 == null) {
    // return "";
    // }
    // return h5.text();
    // }

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
        String replaceText = text.replace(" ", "");
        replaceText = replaceText.replace("　", "");

        if (replaceText.contains("◯")) {
            return Status.NORMAL;
        }
        else if (replaceText.contains("×")) {
            return Status.CANCEL;
        }
        else {
            return Status.CAUTION;
        }
    }

    private static ArrayList<Port> getAnneiPortArray() {
        ArrayList<Port> list = new ArrayList<>();
        list.add(Port.TAKETOMI);
        list.add(Port.KUROSHIMA);
        list.add(Port.KOHAMA);
        list.add(Port.OOHARA);
        list.add(Port.UEHARA);
        list.add(Port.HATOMA);
        return list;
    }
}
