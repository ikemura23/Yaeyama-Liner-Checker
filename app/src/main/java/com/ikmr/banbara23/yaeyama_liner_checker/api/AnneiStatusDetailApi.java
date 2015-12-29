
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Consts;
import com.ikmr.banbara23.yaeyama_liner_checker.PortUrlUtil;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 指定した安栄の詳細を取得
 */
public class AnneiStatusDetailApi {
    public static Observable<String> request(final Port port) {
        return Observable
                .create(new Observable.OnSubscribe<Document>() {
                    @Override
                    public void call(Subscriber<? super Document> subscriber) {
                        Document document;
                        try {
                            document = Jsoup.connect(PortUrlUtil.getAneiPortUrl(port)).timeout(Consts.CONNECTION_TIME_OUT).get();
                            subscriber.onNext(document);
                            subscriber.onCompleted();
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .map(new Func1<Document, String>() {
                    @Override
                    public String call(Document document) {
                        return AnneiDetailParser.pars(document);
                    }
                });
    }
}
