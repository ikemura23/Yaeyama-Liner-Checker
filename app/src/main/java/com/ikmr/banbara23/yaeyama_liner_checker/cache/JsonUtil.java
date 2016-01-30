
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * moshiでjsonを変換したり戻したり
 */
public class JsonUtil {
    public static Object fromJson(String json) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Result> jsonAdapter = moshi.adapter(Result.class);
        try {
            return jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(Result result) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Result> jsonAdapter = moshi.adapter(Result.class);
        return jsonAdapter.toJson(result);
    }
}
