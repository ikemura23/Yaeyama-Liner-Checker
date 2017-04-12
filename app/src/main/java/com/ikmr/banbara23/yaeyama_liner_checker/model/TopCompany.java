package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Company;

public class TopCompany implements Parcelable {
    private boolean allNormalFlag;
    private boolean cancelFlag;
    private boolean cationFlag;
    private boolean suspendFlag;
    private String comment;
    private SimpleArrayMap<Company, StatusInfo> portStatus;

    protected TopCompany(Parcel in) {
        allNormalFlag = in.readByte() != 0x00;
        cancelFlag = in.readByte() != 0x00;
        cationFlag = in.readByte() != 0x00;
        suspendFlag = in.readByte() != 0x00;
        comment = in.readString();
        portStatus = (SimpleArrayMap) in.readValue(SimpleArrayMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (allNormalFlag ? 0x01 : 0x00));
        dest.writeByte((byte) (cancelFlag ? 0x01 : 0x00));
        dest.writeByte((byte) (cationFlag ? 0x01 : 0x00));
        dest.writeByte((byte) (suspendFlag ? 0x01 : 0x00));
        dest.writeString(comment);
        dest.writeValue(portStatus);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TopCompany> CREATOR = new Parcelable.Creator<TopCompany>() {
        @Override
        public TopCompany createFromParcel(Parcel in) {
            return new TopCompany(in);
        }

        @Override
        public TopCompany[] newArray(int size) {
            return new TopCompany[size];
        }
    };

    public boolean isAllNormalFlag() {
        return allNormalFlag;
    }

    public void setAllNormalFlag(boolean allNormalFlag) {
        this.allNormalFlag = allNormalFlag;
    }

    public boolean isCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(boolean cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public boolean isCationFlag() {
        return cationFlag;
    }

    public void setCationFlag(boolean cationFlag) {
        this.cationFlag = cationFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSuspendFlag() {
        return suspendFlag;
    }

    public void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public StatusInfo getStatus(Company company) {
        if (portStatus.containsKey(company)) {
            return portStatus.get(company);
        }
        return null;
    }

    public void setStatus(Company company, StatusInfo statusInfo) {
        portStatus.put(company, statusInfo);
    }
}