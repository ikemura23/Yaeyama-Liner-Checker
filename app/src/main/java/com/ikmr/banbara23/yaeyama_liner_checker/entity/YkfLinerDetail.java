
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by banbara23 on 15/10/18.
 */
public class YkfLinerDetail implements Parcelable {

    String mTitle;
    String mUpdateTime;
    Liner mLiner;
    Port mPort;

    public YkfLinerDetail() {
    }

    protected YkfLinerDetail(Parcel in) {
        mTitle = in.readString();
        mUpdateTime = in.readString();
        mLiner = (Liner) in.readValue(Liner.class.getClassLoader());
        mPort = (Port) in.readValue(Port.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mUpdateTime);
        dest.writeValue(mLiner);
        dest.writeValue(mPort);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<YkfLinerDetail> CREATOR = new Parcelable.Creator<YkfLinerDetail>() {
        @Override
        public YkfLinerDetail createFromParcel(Parcel in) {
            return new YkfLinerDetail(in);
        }

        @Override
        public YkfLinerDetail[] newArray(int size) {
            return new YkfLinerDetail[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public Liner getLiner() {
        return mLiner;
    }

    public void setLiner(Liner liner) {
        mLiner = liner;
    }

    public Port getPort() {
        return mPort;
    }

    public void setPort(Port port) {
        mPort = port;
    }
}
