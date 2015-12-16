
package com.ikmr.banbara23.yaeyama_liner_checker.parser;

import com.ikmr.banbara23.yaeyama_liner_checker.StringUtils;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

/**
 * パース処理の共通クラス
 */
public class ParseUtil {
    public static Port getStrContainPort(String text) {

        if (StringUtils.isEmpty(text))
            return null;
                
        Port port = text.contains(Port.HATERUMA.getPortSimple()) ? Port.HATERUMA
                : text.contains(Port.KUROSHIMA.getPortSimple()) ? Port.KUROSHIMA
                : text.equals("鳩間島経由西表島上原") ? Port.HATOMA_UEHARA
                : text.contains(Port.UEHARA.getPortSimple()) ? Port.UEHARA
                : text.contains(Port.HATOMA.getPortSimple()) ? Port.HATOMA
                : text.contains(Port.KOHAMA.getPortSimple()) ? Port.KOHAMA
                : text.contains(Port.OOHARA.getPortSimple()) ? Port.OOHARA
                : text.contains(Port.TAKETOMI.getPortSimple()) ? Port.TAKETOMI
                : text.contains(Port.PREMIUM_DREAM.getPortSimple()) ? Port.PREMIUM_DREAM
                : text.contains(Port.SUPER_DREAM.getPortSimple()) ? Port.SUPER_DREAM
                : null;

        return port;
    }
}
