
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import com.google.gson.Gson;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;

/**
 * moshiでjsonを変換したり戻したり
 */
public class JsonUtil {
    public static Result fromJson(String json) {
        return new Gson().fromJson(json, Result.class);
    }

    public static String toJsonFromResult(Result result) {
        return new Gson().toJson(result);
    }
}
