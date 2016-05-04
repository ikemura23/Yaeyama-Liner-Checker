
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import com.crashlytics.android.Crashlytics;
import com.ikmr.banbara23.yaeyama_liner_checker.Base;
import com.ikmr.banbara23.yaeyama_liner_checker.R;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.entity.Result;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * moshiでjsonを変換したり戻したり
 */
public class JsonUtil {
    public static Result fromJson(String json) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Result> jsonAdapter = moshi.adapter(Result.class);
        try {
            return jsonAdapter.fromJson(json);
        } catch (IOException e) {
            Crashlytics.logException(e);
            return null;
        }
    }

    // public static String toJson(Parcelable parcelable) {
    // Moshi moshi = new Moshi.Builder().build();
    // JsonAdapter<Parcelable> jsonAdapter = moshi.adapter(Parcelable.class);
    // return jsonAdapter.toJson(parcelable);
    // }

    public static String toJsonFromResult(Result result) {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(Result.class).toJson(result);
    }

    public static String getLocalResultJson(Company company) {

        switch (company) {
            case ANNEI:
                return Base.getContext().getString(R.string.annei_result_json);
            case YKF:
                return Base.getContext().getString(R.string.ykf_result_json);
            case DREAM:
                return Base.getContext().getString(R.string.dream_result_json);
            default:
                return null;
        }
    }
}
