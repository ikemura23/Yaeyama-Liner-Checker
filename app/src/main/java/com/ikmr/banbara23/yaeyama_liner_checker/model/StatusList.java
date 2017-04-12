package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Port;

class StatusList implements Parcelable {
    private String comment;
    private SimpleArrayMap portStatuses;

    private class PortStatus {
        Port port;
        String comment;
        String name;
        StatusInfo statusInfo;
    }

    protected StatusList(Parcel in) {
        comment = in.readString();
        portStatuses = (SimpleArrayMap) in.readValue(SimpleArrayMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeValue(portStatuses);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusList> CREATOR = new Parcelable.Creator<StatusList>() {
        @Override
        public StatusList createFromParcel(Parcel in) {
            return new StatusList(in);
        }

        @Override
        public StatusList[] newArray(int size) {
            return new StatusList[size];
        }
    };
}
