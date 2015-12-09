
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.os.AsyncTask;
import android.util.Log;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.AnneiDetailParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class StatusDetailAsync extends AsyncTask<String, Integer, Document> {
    // Activityへのコールバック用interface
    public interface DetailAsyncCallback {
        void preExecute();

        void postExecute(String result);

        // void progressUpdate(int progress);

        // void cancel();
    }

    private DetailAsyncCallback callback = null;;
    private Company mCompany;

    public StatusDetailAsync(DetailAsyncCallback _callback, Company company) {
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
        if (callback == null) {
            return;
        }
        if (document == null) {
            callback.postExecute(null);
        }
        String result = AnneiDetailParser.pars(document);
        callback.postExecute(result);
    }

    // @Override
    // protected void onProgressUpdate(Integer... values) {
    // super.onProgressUpdate(values);
    // callback.progressUpdate(values[0]);
    // }

    @Override
    protected Document doInBackground(String... params) {
        Document doc = null;
        Log.d("StatusAsyncTaskLoader", "loadInBackground");
        try {
            // HTML取得 タイムアウトは10秒
            doc = Jsoup.connect(params[0]).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
