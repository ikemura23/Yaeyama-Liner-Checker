
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.os.Parcelable;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * moshiでjsonを変換したり戻したり
 */
public class JsonUtil {
    public static Parcelable fromJson(String json) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Parcelable> jsonAdapter = moshi.adapter(Parcelable.class);
        try {
            return jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(Parcelable parcelable) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Parcelable> jsonAdapter = moshi.adapter(Parcelable.class);
        return jsonAdapter.toJson(parcelable);
    }
}
