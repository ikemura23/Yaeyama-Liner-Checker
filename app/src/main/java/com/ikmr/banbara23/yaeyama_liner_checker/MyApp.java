
package com.ikmr.banbara23.yaeyama_liner_checker;

import timber.log.Timber;

import android.app.Application;

/**
 * Created by banbara23 on 15/09/19.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

    /**
     * AdUnitId
     * 
     * @return AdUnitId
     */
    public String getAdUnitId() {
        if (BuildConfig.DEBUG) {
            return getApplicationContext().getString(R.string.banner_ad_unit_id);
        } else {
            return getSecureBannerId();
        }
    }

    /**
     * issue_4で対応
     * 
     * @return AdUnitId
     */
    private String getSecureBannerId() {
        return new Object() {
            int t;

            public String toString() {
                byte[] buf = new byte[10];
                t = -1106962033;
                buf[0] = (byte) (t >>> 3);
                t = -1066460846;
                buf[1] = (byte) (t >>> 7);
                t = -1866426777;
                buf[2] = (byte) (t >>> 1);
                t = 1580243504;
                buf[3] = (byte) (t >>> 7);
                t = 676159246;
                buf[4] = (byte) (t >>> 14);
                t = 494155327;
                buf[5] = (byte) (t >>> 8);
                t = 622087616;
                buf[6] = (byte) (t >>> 6);
                t = -1282613599;
                buf[7] = (byte) (t >>> 20);
                t = 1508143754;
                buf[8] = (byte) (t >>> 9);
                t = 27662589;
                buf[9] = (byte) (t >>> 13);
                return new String(buf);
            }
        }.toString();
    }
}
