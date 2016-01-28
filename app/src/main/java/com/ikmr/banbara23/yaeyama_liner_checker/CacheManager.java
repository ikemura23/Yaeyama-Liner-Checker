
package com.ikmr.banbara23.yaeyama_liner_checker;

/**
 * キャッシュ管理マネージャー
 */
public class CacheManager {
    private static CacheManager sCacheManager;

    public static CacheManager getInstance() {
        if (sCacheManager == null)
            sCacheManager = new CacheManager();
        return sCacheManager;
    }

}
