
package com.ikmr.banbara23.yaeyama_liner_checker;

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
        for (Element d : div) {
            mLiners.add(getDivPort(d));
        }

        // div.get(0).tag()
        // String imagePath = "http:" + image.attr("src");

        // AQuery imageView = new AQuery(this);
        // imageView.id(R.id.imageView).visible().webImage(imagePath, true,
        // false, 0);
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
     * @param div
     * @return
     */
    private static Liner getDivPort(Element div) {
        Liner liner = new Liner();
        liner.setPort(Port.HATERUMA);

        if (div == null) {
            return null;
        }
        Elements h6 = div.getElementsByTag("h6");
        if (h6.text().contains("波照間")) {
            Elements p = div.getElementsByTag("p");

            if (p.get(0).hasClass("normal")) {
                liner.setStatus(Status.NORMAL);
            }
            liner.setText(p.text());
        }

        return null;
    }
}
