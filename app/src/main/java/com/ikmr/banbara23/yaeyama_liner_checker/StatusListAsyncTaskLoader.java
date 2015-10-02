
package com.ikmr.banbara23.yaeyama_liner_checker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

/**
 * Created by banbara23 on 15/10/03.
 */
public class StatusListAsyncTaskLoader extends AsyncTaskLoader<Document> {
    Company mCompany;
    String mUrl;

    public StatusListAsyncTaskLoader(Context context, Company company, String url) {
        super(context);
        this.mCompany = company;
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
