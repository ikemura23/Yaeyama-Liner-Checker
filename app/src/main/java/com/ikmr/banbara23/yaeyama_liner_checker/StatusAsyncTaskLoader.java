
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 指定HTMLを取得する
 */
public class StatusAsyncTaskLoader extends AsyncTaskLoader<Document> {
    String mUrl;

    public StatusAsyncTaskLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        Log.d("StatusAsyncTaskLoader", "onStartLoading");
    }

    @Override
    public Document loadInBackground() {
        Document doc = null;
        Log.d("StatusAsyncTaskLoader", "loadInBackground");
        try {
            // HTML取得 タイムアウトは10秒
            doc = Jsoup.connect(mUrl).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
