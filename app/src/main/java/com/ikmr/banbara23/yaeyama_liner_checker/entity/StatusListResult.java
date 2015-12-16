
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * 運航一覧のhtmlをパースした結果を格納するクラス
 */
public class StatusListResult implements Parcelable {

    private Company mCompany;
    private String mUpdateTime;
    private String mComment;
    HashMap<Port, Liner> mLiners = new HashMap<>();

    public StatusListResult() {
    }

    protected StatusListResult(Parcel in) {
        mCompany = (Company) in.readValue(Company.class.getClassLoader());
        mUpdateTime = in.readString();
        mComment = in.readString();
        mLiners = (HashMap) in.readValue(HashMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mCompany);
        dest.writeString(mUpdateTime);
        dest.writeString(mComment);
        dest.writeValue(mLiners);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusListResult> CREATOR = new Parcelable.Creator<StatusListResult>() {
        @Override
        public StatusListResult createFromParcel(Parcel in) {
            return new StatusListResult(in);
        }

        @Override
        public StatusListResult[] newArray(int size) {
            return new StatusListResult[size];
        }
    };

    public Company getCompany() {
        return mCompany;
    }

    public void setCompany(Company company) {
        mCompany = company;
    }

    public String getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        mUpdateTime = updateTime;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public HashMap<Port, Liner> getLiners() {
        return mLiners;
    }

    public void setLiners(HashMap<Port, Liner> liners) {
        mLiners = liners;
    }
}
