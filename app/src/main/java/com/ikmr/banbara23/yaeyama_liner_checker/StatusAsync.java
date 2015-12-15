
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class StatusAsync extends AsyncTask<String, Integer, Document> {

    private static final int CONNECTION_TIME_OUT = 20000;

    // Activiyへのコールバック用interface
    public interface AsyncTaskCallback {
        void preExecute();

        void postExecute(Document result);

        // void progressUpdate(int progress);

        // void cancel();
    }

    private AsyncTaskCallback callback = null;;

    public StatusAsync(AsyncTaskCallback _callback) {
        this.callback = _callback;
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
    protected void onPostExecute(Document result) {
        super.onPostExecute(result);
        if (callback != null) {
            callback.postExecute(result);
        }
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
            doc = Jsoup.connect(params[0]).timeout(CONNECTION_TIME_OUT).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
