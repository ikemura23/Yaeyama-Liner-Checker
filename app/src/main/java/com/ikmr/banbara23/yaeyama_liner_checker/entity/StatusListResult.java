
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by banbara23 on 15/09/21.
 */
public class StatusListResult implements Parcelable {

    private Company mCompany;
    private String mUpdateTime;
    private String mTitle;
    ArrayList<Liner> mLiners = new ArrayList<>();

    public StatusListResult() {
    }

    protected StatusListResult(Parcel in) {
        mCompany = (Company) in.readValue(Company.class.getClassLoader());
        mUpdateTime = in.readString();
        mTitle = in.readString();
        if (in.readByte() == 0x01) {
            mLiners = new ArrayList<Liner>();
            in.readList(mLiners, Liner.class.getClassLoader());
        } else {
            mLiners = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mCompany);
        dest.writeString(mUpdateTime);
        dest.writeString(mTitle);
        if (mLiners == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mLiners);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<StatusListResult> CREATOR = new Creator<StatusListResult>() {
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Liner> getLiners() {
        return mLiners;
    }

    public void setLiners(ArrayList<Liner> liners) {
        mLiners = liners;
    }
}
