
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by banbara23 on 15/09/21.
 */
public class Result implements Parcelable {

    private Company mCompany;
    private String mUpdateTime;
    private String mTitle;
    ArrayList<Liner> mLiners = new ArrayList<>();

    public Result() {
    }

    protected Result(Parcel in) {
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
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
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
