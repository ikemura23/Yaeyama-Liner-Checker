
package com.ikmr.banbara23.yaeyama_liner_checker.util;

import android.content.Context;

import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Liner;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Price;

import java.util.ArrayList;

/**
 * 港のutilクラス
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

    /**
     * 安栄の料金を返す
     * 
     * @param context
     * @param port
     * @return
     */
    public static Price getAnneiPrice(Context context, Port port) {
        Price price = new Price();
        price.setAdult(getAnneiAdultPrice(context, port));
        price.setChild(getAnneiChildPrice(context, port));
        price.setHandicapped(getAnneiChildPrice(context, port));
        return price;
    }

    /**
     * 安栄の大人料金を返す
     * 
     * @param context
     * @param port 港
     * @return 料金
     */
    public static String getAnneiAdultPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_adult);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_adult);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_adult);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_adult);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_adult);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_adult);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_adult);
            default:
                return null;
        }
    }

    /**
     * 安栄の子供料金を返す
     *
     * @param context
     * @param port 港
     * @return 料金
     */
    public static String getAnneiChildPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_child);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_child);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_child);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_child);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_child);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_child);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_child);
            default:
                return null;
        }
    }

    /**
     * 安栄の障害者料金を返す
     *
     * @param context
     * @param port 港
     * @return 料金
     */
    public static String getAnneiHandicappedPrice(Context context, Port port) {
        switch (port) {
            case HATERUMA:
                return context.getString(R.string.annei_hateruma_price_handicapped);
            case UEHARA:
                return context.getString(R.string.annei_uehara_price_handicapped);
            case OOHARA:
                return context.getString(R.string.annei_oohara_price_handicapped);
            case HATOMA:
                return context.getString(R.string.annei_hatoma_price_handicapped);
            case KUROSHIMA:
                return context.getString(R.string.annei_kuroshima_price_handicapped);
            case KOHAMA:
                return context.getString(R.string.annei_kohama_price_handicapped);
            case TAKETOMI:
                return context.getString(R.string.annei_taketomi_price_handicapped);
            default:
                return null;
        }
    }
}
