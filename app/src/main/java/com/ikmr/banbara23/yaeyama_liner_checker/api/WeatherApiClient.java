
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Weather;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

;

/**
 * 安栄一覧の取得
 */
public class WeatherApiClient {

    /***
     * RxJavaで一覧取得
     *
     * @return
     */
    public static Observable<Weather> request() {
        return Observable
                .create(new ObservableOnSubscribe<Weather>() {
                    @Override
                    public void subscribe(ObservableEmitter<Weather> emitter) {
                        String tableName = "Weather";
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(tableName);
                        query.setLimit(1);
                        query.addOrderByDescending(Base.getContext().getString(R.string.NCMB_sort_column_name));
                        List<NCMBObject> results = null;
                        try {
                            results = query.find();
                        } catch (NCMBException e) {
                            emitter.onError(e);
                        }
                        if (results == null) {
                            emitter.onError(new Exception("データが空"));
                            return;
                        }
                        NCMBObject object = results.get(0);
                        String json = object.getString("weather");
                        Weather weather = new Gson().fromJson(json, Weather.class);
                        emitter.onNext(weather);
                        emitter.onComplete();
                    }
                });
    }
}
