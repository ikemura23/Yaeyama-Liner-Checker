package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

public class StatusInfo implements Parcelable {
    private Status status;
    private String comment;

    public StatusInfo() {
    }

    protected StatusInfo(Parcel in) {
        status = (Status) in.readValue(Status.class.getClassLoader());
        comment = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeString(comment);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusInfo> CREATOR = new Parcelable.Creator<StatusInfo>() {
        @Override
        public StatusInfo createFromParcel(Parcel in) {
            return new StatusInfo(in);
        }

        @Override
        public StatusInfo[] newArray(int size) {
            return new StatusInfo[size];
        }
    };

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
