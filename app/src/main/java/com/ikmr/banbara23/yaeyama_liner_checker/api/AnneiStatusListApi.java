
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
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
public class AnneiStatusListApi {

    /**
     * RxAndroidを利用
     *
     * @param url
     * @return Observable<Result>
     */
    public static Observable<Result> request(final String url) {
        return Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        NCMBQuery<NCMBObject> query = new NCMBQuery<>(Base.getContext().getString(R.string.NCMB_annei_table));
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
                            subscriber.onNext(json);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }

                        // query.findInBackground(new FindCallback<NCMBObject>()
                        // {
                        // @Override
                        // public void done(List<NCMBObject> results,
                        // NCMBException e) {
                        // if (e != null) {
                        // Log.d("TopActivity", "e:" + e);
                        // // 検索失敗時の処理
                        // } else {
                        // Log.d("TopActivity", "results:" + results);
                        // // 検索成功時の処理
                        // }
                        // }
                        // });
                        // Document document;
                        // try {
                        // document =
                        // Jsoup.connect(url).timeout(Const.CONNECTION_TIME_OUT).get();
                        // // subscriber.onNext(document);
                        // subscriber.onCompleted();
                        // } catch (IOException e) {
                        // subscriber.onError(e);
                        // }
                    }
                })
                .map(new Func1<String, Result>() {
                    @Override
                    public Result call(String json) {
                        return new Gson().fromJson(json, Result.class);
                    }
                });
    }
}
