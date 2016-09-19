package com.ikmr.banbara23.yaeyama_liner_checker.entity;

/**
 * レコード単体
 */
public class Record {
    String time;
    StatusInfo statusInfo;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    @Override
    public String toString() {
        return "Record{" +
                "time='" + time + '\'' +
                ", statusInfo=" + statusInfo +
                '}';
    }
}
