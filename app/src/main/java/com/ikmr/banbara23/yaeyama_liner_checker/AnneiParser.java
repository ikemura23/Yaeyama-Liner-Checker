
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.util.Log;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 安栄HTMLのパース処理
 */
public class AnneiParser {

    public static void pars(Document doc) {
        Result result = new Result();
        ArrayList<Liner> mLiners = new ArrayList<>();

        // <div id="liner"> 取得
        Element liner = doc.getElementById("liner");
        if (liner == null) {
            return;
        }
        // <div class="box"> 取得
        Elements boxs = liner.getElementsByClass("box");
        if (boxs == null || boxs.get(0) == null) {
            return;
        }
        Element box = boxs.get(0);

        // 更新日
        Elements h4 = box.getElementsByTag("h4");
        result.setUpdateTime(getUpdateTime(h4));

        // タイトル
        Elements h5 = box.getElementsByTag("h5");
        result.setTitle(getTitle(h5));

        // 港別の運航状況
        Elements div = box.getElementsByTag("div");
        ArrayList<String> array = getAnneiPortArray();
        for (String port : array) {
            mLiners.add(getDivPort(port, div));
        }
        result.setLiners(mLiners);
    }

    /**
     * 更新日時の取得
     *
     * @param h4 タグ
     * @return 更新日時
     */
    private static String getUpdateTime(Elements h4) {
        if (h4 == null) {
            return "";
        }
        return h4.text();
    }

    /**
     * タイトル取得
     *
     * @param h5 タグ
     * @return タイトル
     */
    private static String getTitle(Elements h5) {
        if (h5 == null) {
            return "";
        }
        return h5.text();
    }

    /**
     * 波照間
     * 
     * @param port 港名
     * @param div <div class="box">
     * @return
     */
    private static Liner getDivPort(String port, Elements div) {
        Liner liner = new Liner();
        liner.setPort(Port.HATERUMA);

        if (div == null) {
            return null;
        }
        Elements devChild = div.get(0).children();
        for (int i = 0; i < devChild.size(); i++) {
            
            Log.d("AnneiParser", "child.get(0):" + devChild.get(i));

        }
        // for (Element d : div) {
        //
        // Elements h6 = d.getElementsByTag("h6");
        // if (h6.text().contains(port)) {
        // Elements p = d.getElementsByTag("p");
        //
        // // <p>タグのクラスを判定
        // if (p.get(0).hasClass("normal")) {
        // // 通常運航
        // liner.setStatus(Status.NORMAL);
        // }
        // else if (p.get(0).hasClass("cancel")) {
        // // 欠航有り
        // liner.setStatus(Status.CANCEL);
        // }
        // else {
        // // 運航にも欠航にも当てはまらないもの、未定とか
        // liner.setStatus(Status.CAUTION);
        // }
        //
        // // テキストを取得
        // liner.setText(p.text());
        // return liner;
        // }
        // }

        return liner;
    }

    private static ArrayList<String> getAnneiPortArray() {
        ArrayList<String> list = new ArrayList<>();
        list.add("波照間");
        list.add("上原");
        list.add("鳩間島");
        list.add("大原");
        list.add("黒島");
        list.add("小浜");
        list.add("竹富");
        return list;
    }
}
