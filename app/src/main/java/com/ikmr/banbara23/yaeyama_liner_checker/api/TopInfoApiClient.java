
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.TopInfo;
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
    public Observable<TopInfo> request() {
        return Observable
                .create(new Observable.OnSubscribe<NCMBObject>() {
                    @Override
                    public void call(Subscriber<? super NCMBObject> subscriber) {
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(Const.NcmbTable.TopInfo);
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
                .map(new Func1<NCMBObject, TopInfo>() {
                    @Override
                    public TopInfo call(NCMBObject ncmbObject) {
                        return new NcmbConverter().convertToTopInfo(ncmbObject);
                    }
                });
    }
}
