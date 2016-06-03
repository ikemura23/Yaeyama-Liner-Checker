package api;

import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by kikemurx on 2016/06/03.
 */
public class StatusListApi {
    public static Observable<Result> request(final Company company) {
        return Observable
                .create(new Observable.OnSubscribe<Result>() {
                    @Override
                    public void call(Subscriber<? super Result> subscriber) {
                        String ncmbTableName = null;
                        switch (company) {
                            case ANNEI:
                                ncmbTableName = Base.getContext().getString(R.string.NCMB_annei_table);
                                break;
                            case YKF:
                                ncmbTableName = Base.getContext().getString(R.string.NCMB_ykf_table);
                                break;
                            case DREAM:
                                ncmbTableName = Base.getContext().getString(R.string.NCMB_dream_table);
                                break;
                        }
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(ncmbTableName);
                        query.setLimit(1);
                        query.addOrderByDescending(Base.getContext().getString(R.string.NCMB_sort_column_name));
                        List<NCMBObject> results = null;
                        try {
                            results = query.find();
                        } catch (NCMBException e) {
                            subscriber.onError(e);
                        }
                        try {
                            NCMBObject object = results.get(0);
                            String json = object.getString(Base.getContext().getString(R.string.NCMB_get_column_name));
                            subscriber.onNext(new Result());
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                });
    }
}
