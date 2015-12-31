
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Context;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import java.util.ArrayList;

/**
 * Created by banbara23 on 15/10/10.
 */
public class PortUtil {

    /**
     * 引数で渡された港の詳細のurlを取得する
     * 
     * @param context
     * @param port
     * @return
     */
    public static String getAnneiDetailUrl(Context context, Port port) {

        switch (port) {
            case HATERUMA:
                return context.getString(R.string.url_annei_hateruma);
            case UEHARA:
                return context.getString(R.string.url_annei_uehara);
            case OOHARA:
                return context.getString(R.string.url_annei_oohara);
            case HATOMA:
                return context.getString(R.string.url_annei_hateruma);
            case KUROSHIMA:
                return context.getString(R.string.url_annei_kuroshima);
            case KOHAMA:
                return context.getString(R.string.url_annei_kohama);
            case TAKETOMI:
                return context.getString(R.string.url_annei_taketomi);
            default:
                return null;
        }
    }

    /**
     * 港の配列から希望する港を取得する
     * 
     * @param liners 一覧データ
     * @param pot 希望する港
     * @return 欲しい港
     */
    public static Liner getMyPort(ArrayList<Liner> liners, Port pot) {
        for (Liner liner : liners) {
            if (pot == liner.getPort()) {
                return liner;
            }
        }
        return null;
    }
}
