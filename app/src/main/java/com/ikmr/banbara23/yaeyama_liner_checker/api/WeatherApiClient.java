
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Weather;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 安栄一覧の取得
 */
public class WeatherApiClient {

    /***
     * RxJavaで一覧取得
     *
     * @return
     */
    public Observable<Weather> request() {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String tableName = "Weather";
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(tableName);
                        query.setLimit(1);
                        List<NCMBObject> results = null;
                        try {
                            results = query.find();
                        } catch (NCMBException e) {
                            subscriber.onError(e);
                        }
                        if (results == null) {
                            subscriber.onError(new Exception("データが空"));
                            return;
                        }
                        NCMBObject object = results.get(0);
                        String json = object.getString("weather");
                        subscriber.onNext(json);
                        subscriber.onCompleted();
                    }
                })
                .map(new Func1<String, Weather>() {
                    @Override
                    public Weather call(String json) {
                        return new Gson().fromJson(json, Weather.class);
                    }
                });
    }
}
