
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.os.AsyncTask;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.StatusListResult;
import com.ikmr.banbara23.yaeyama_liner_checker.parser.DreamListParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindString;

public class DreamStatusListAsync extends AsyncTask<String, Integer, Document> {

    // 取得URL
    @BindString(R.string.url_dream_list)
    String URL;

    // Activityへのコールバック用interface
    public interface DreamStatusListAsyncCallback {
        void preExecute();

        void postExecute(StatusListResult statusListResult);

        // void progressUpdate(int progress);

        void cancel();
    }

    private DreamStatusListAsyncCallback callback = null;;

    public DreamStatusListAsync(DreamStatusListAsyncCallback _callback) {
        this.callback = _callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.preExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.cancel();
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        if (callback == null) {
            return;
        }
        if (document == null) {
            callback.postExecute(null);
        }

        StatusListResult statusListResult = DreamListParser.pars(document);
        callback.postExecute(statusListResult);
    }

    // @Override
    // protected void onProgressUpdate(Integer... values) {
    // super.onProgressUpdate(values);
    // callback.progressUpdate(values[0]);
    // }

    @Override
    protected Document doInBackground(String... params) {
        Document doc = null;
        try {
            // HTML取得
            doc = Jsoup.connect(URL).timeout(Consts.CONNECTION_TIME_OUT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
