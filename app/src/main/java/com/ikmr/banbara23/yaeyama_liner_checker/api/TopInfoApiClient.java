
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
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
public class TopInfoApiClient {

    /***
     * RxJavaで一覧取得
     *
     * @return
     */
    public Observable<Weather> request() {
        return Observable
                .create(new Observable.OnSubscribe<NCMBObject>() {
                    @Override
                    public void call(Subscriber<? super NCMBObject> subscriber) {
                        String tableName = "TopInfo";
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(tableName);
                        query.setLimit(1);
                        query.addOrderByDescending(Base.getContext().getString(R.string.NCMB_sort_column_name));
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
                        subscriber.onNext(object);
                        subscriber.onCompleted();
                    }
                })
                .map(new Func1<NCMBObject, Weather>() {
                    @Override
                    public Weather call(NCMBObject ncmbObject) {
                        return null;
                    }
                });
    }
}
