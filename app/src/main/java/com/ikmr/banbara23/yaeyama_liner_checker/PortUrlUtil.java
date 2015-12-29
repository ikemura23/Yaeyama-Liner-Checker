
package com.ikmr.banbara23.yaeyama_liner_checker;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

import butterknife.BindString;

/**
 * 港別のURLを返す
 */
public class PortUrlUtil {

    // 安栄 -------------------------------------
    @BindString(R.string.url_annei_hateruma)
    static String AnneiHaterumaUrl;

    @BindString(R.string.url_annei_hatoma)
    static String AnneiHatomaUrl;

    @BindString(R.string.url_annei_kohama)
    static String AnneiKohamaUrl;

    @BindString(R.string.url_annei_kuroshima)
    static String AnneiKuroshimaUrl;

    @BindString(R.string.url_annei_oohara)
    static String AnneiOoharaUrl;

    @BindString(R.string.url_annei_taketomi)
    static String AnneiTaketomiUrl;

    @BindString(R.string.url_annei_uehara)
    static String AnneiUeharaUrl;

    /***
     * 安栄の港別のURLを返す
     *
     * @return URL文字列
     */
    public static String getAneiPortUrl(Port port) {
        switch (port) {
            case TAKETOMI:
                return AnneiTaketomiUrl;
            case KOHAMA:
                return AnneiKohamaUrl;
            case OOHARA:
                return AnneiOoharaUrl;
            case UEHARA:
                return AnneiUeharaUrl;
            case KUROSHIMA:
                return AnneiKuroshimaUrl;
            case HATOMA:
                return AnneiHatomaUrl;
            case HATERUMA:
                return AnneiHaterumaUrl;
            default:
                return "";
        }
    }
}
