
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.os.AsyncTask;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiListParser;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.YkfParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class StatusListAsync extends AsyncTask<String, Integer, Document> {

    Company mCompany;

    // Jsoup接続タイムアウト
    private final static int CONNECTION_TIME_OUT = 10000;

    // コールバック用interface
    public interface StatusListAsyncCallback {
        void preExecute();

        void postExecute(Result result);

        // void progressUpdate(int progress);

        // void cancel();
    }

    private StatusListAsyncCallback callback = null;;

    public StatusListAsync(StatusListAsyncCallback _callback, Company company) {
        this.callback = _callback;
        mCompany = company;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.preExecute();
    }

    // @Override
    // protected void onCancelled() {
    // super.onCancelled();
    // callback.cancel();
    // }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        Result result;
        if (mCompany == Company.ANNEI) {
            // 安栄のHTMLパース呼び出し
            result = AnneiListParser.pars(document);
        } else {
            // 八重山観光フェリーのHTMLパース呼び出し
            result = YkfParser.pars(document);
        }
        if (callback != null) {
            callback.postExecute(result);
        }
    }

    @Override
    protected Document doInBackground(String... params) {
        Document doc = null;
        try {
            // HTML取得 タイムアウトは10秒
            doc = Jsoup.connect(params[0]).timeout(CONNECTION_TIME_OUT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
