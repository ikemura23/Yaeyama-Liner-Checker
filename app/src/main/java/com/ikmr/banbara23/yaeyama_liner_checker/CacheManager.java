
package com.ikmr.banbara23.yaeyama_liner_checker;
import android.os.*;

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
	
	public boolean hasCashe(){
		
		return true;	
	}

	public Parcelable get(String key) {
		
		return null;
	}
	
	public void put(String key) {

	}
	
	public boolean isPassSettingTime (){

		return true;	
	}
	
	
}
