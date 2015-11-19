
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import android.text.TextUtils;

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
public class AnneiListParser {

    public static Result pars(Document doc) {
        if (doc == null) {
            return null;
        }
        Result result = new Result();
        ArrayList<Liner> mLiners = new ArrayList<>();

        // <div id="liner"> 取得
        Element liner = doc.getElementById("liner");
        if (liner == null) {
            return null;
        }
        // <div class="box"> 取得
        Elements boxs = liner.getElementsByClass("box");
        if (boxs == null || boxs.get(0) == null) {
            return null;
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
        ArrayList<Port> array = getAnneiPortArray();
        for (Port port : array)
            mLiners.add(getDivPort(port, div));
        result.setLiners(mLiners);
        return result;
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
    private static Liner getDivPort(Port port, Elements div) {
        Liner liner = new Liner();
        liner.setPort(port);

        if (div == null) {
            return null;
        }
        Elements devChild = div.get(0).children();
        for (int i = 0; i < devChild.size(); i++) {

            // div.boxの中身を取得
            Element element = devChild.get(i);

            // h6タグに港名が入っている
            Elements h6 = element.getElementsByTag("h6");
            if (TextUtils.isEmpty(h6.text())) {
                // 空白は飛ばす
                continue;
            }
            // h6タグの中身と引数の港名が一致しているか？
            if (h6.text().contains(port.getPortSimple())) {
                // <p>タグの中身を取得、以下はサンプル
                // <p class="normal"><a href="liner/kohamajima.html"
                // title="小浜島航路 時刻表へ">通常運航</a></p>
                Elements p = element.getElementsByTag("p");

                // <p>タグのクラスを判定
                if (p.get(0).hasClass("normal")) {
                    // 通常運航
                    liner.setStatus(Status.NORMAL);
                } else if (p.get(0).hasClass("cancel")) {
                    // 欠航有り
                    liner.setStatus(Status.CANCEL);
                } else {
                    // 運航にも欠航にも当てはまらないもの、未定とか
                    liner.setStatus(Status.CAUTION);
                }

                // <p>タグのテキストを取得
                if (TextUtils.isEmpty(p.text())) {
                    // 取得できなかったら以下の文字が一覧に表示される
                    liner.setText("エラー 取得失敗");
                } else {
                    liner.setText(p.text());
                }
                return liner;
            }
        }

        return liner;
    }

    private static ArrayList<Port> getAnneiPortArray() {
        ArrayList<Port> list = new ArrayList<>();
        list.add(Port.HATERUMA);
        list.add(Port.UEHARA);
        list.add(Port.HATOMA);
        list.add(Port.OOHARA);
        list.add(Port.KUROSHIMA);
        list.add(Port.KOHAMA);
        list.add(Port.TAKETOMI);
        return list;
    }
}
