
package com.ikmr.banbara23.yaeyama_liner_checker;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiListParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by banbara23 on 15/12/25.
 */
public class AnneiStatusListApi {
    public static Observable<String> request() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                Document document = null;
                try {
                    document = Jsoup.connect("http://www.aneikankou.co.jp/").timeout(Consts.CONNECTION_TIME_OUT).get();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                Result result = AnneiListParser.pars(document);
                subscriber.onCompleted();
            }
        });
    }
}
