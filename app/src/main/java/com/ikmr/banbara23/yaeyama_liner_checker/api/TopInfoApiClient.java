
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.top.TopInfo;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


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
                .create(new ObservableOnSubscribe<TopInfo>() {
                    @Override
                    public void subscribe(ObservableEmitter<TopInfo> emitter) {
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(Const.NcmbTable.TopInfo);
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
                        TopInfo topInfo = new NcmbConverter().convertToTopInfo(object);
                        emitter.onNext(topInfo);
                        emitter.onComplete();
                    }
                });
    }
}
