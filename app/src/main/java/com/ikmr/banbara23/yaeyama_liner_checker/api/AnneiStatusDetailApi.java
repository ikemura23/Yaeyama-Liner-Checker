
package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.Const;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * 指定した安栄の詳細を取得
 */
public class AnneiStatusDetailApi {
    public static Observable<String> request(final String url) {
        return Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
                        Document document;
                        try {
                            document = Jsoup.connect(url).timeout(Const.CONNECTION_TIME_OUT).get();
                            String value = AnneiDetailParser.pars(document);
                            emitter.onNext(value);
                            emitter.onComplete();
                        } catch (IOException e) {
                            emitter.onError(e);
                        }
                    }
                });
    }
}
