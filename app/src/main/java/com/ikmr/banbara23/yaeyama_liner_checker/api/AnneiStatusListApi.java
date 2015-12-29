
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Consts;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiListParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindString;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 安栄一覧の取得
 */
public class AnneiStatusListApi {

    // 一覧URL
    @BindString(R.string.url_annei_list)
    static String ANNEI_URL;

    /**
     * RxAndroidを利用
     * 
     * @return Observable<Result>
     */
    public static Observable<Result> request() {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {
                    @Override
                    public void call(Subscriber<? super Document> subscriber) {
                        Document document;
                        try {
                            document = Jsoup.connect(ANNEI_URL).timeout(Consts.CONNECTION_TIME_OUT).get();
                            subscriber.onNext(document);
                            subscriber.onCompleted();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .map(new Func1<Document, Result>() {
                    @Override
                    public Result call(Document document) {
                        return AnneiListParser.pars(document);
                    }
                });
    }
}
