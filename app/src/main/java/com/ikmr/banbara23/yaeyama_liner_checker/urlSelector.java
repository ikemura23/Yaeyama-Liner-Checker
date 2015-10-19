package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Context;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

/**
 * Created by banbara23 on 15/10/10.
 */
public class UrlSelector {

    public static String getDetailUrl(Context context, Company company, Port port) {
        switch (company) {
            case ANNEI:
                return getAnneiUrl(context, port);
            case YKF:
                return getYkfUrl(context, port);
            default:
                return null;
        }
    }

    private static String getAnneiUrl(Context context, Port port) {

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


    private static String getYkfUrl(Context context, Port port) {

        switch (port) {
            case HATERUMA:
                return context.getString(R.string.url_ykf_facebook);
            default:
                return null;
        }
    }
}

