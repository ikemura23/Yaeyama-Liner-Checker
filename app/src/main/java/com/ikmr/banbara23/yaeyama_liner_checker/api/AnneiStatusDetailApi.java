
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.util.NcmbUtil;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import rx.Observable;
import rx.Subscriber;

/**
 * 指定した安栄の詳細を取得
 */
public class AnneiStatusDetailApi {
    public static Observable<NCMBObject> request(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<NCMBObject>() {
                    @Override
                    public void call(Subscriber<? super NCMBObject> subscriber) {
                        String linerId = NcmbUtil.getLinerId();
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(Const.NcmbTable.ANEI_DETAIL_TABLE);
                        query.whereEqualTo(Const.NcmbColumn.LINER_ID, linerId);
                        query.addOrderByDescending(Const.NcmbColumn.UPDATE_DATE);
                        try {
                            NCMBObject object = query.find().get(0);
                            subscriber.onNext(object);
                            subscriber.onCompleted();
                        } catch (NCMBException e) {
                            subscriber.onError(e);
                        }
                    }
                });
    }

}
