
package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by banbara23 on 15/10/03.
 */
public class StatusAsyncTaskLoader extends AsyncTaskLoader<Document> {
    String mUrl;

    public StatusAsyncTaskLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    public Document loadInBackground() {
        Document doc = null;

        try {
            // HTML取得
            doc = Jsoup.connect(mUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
