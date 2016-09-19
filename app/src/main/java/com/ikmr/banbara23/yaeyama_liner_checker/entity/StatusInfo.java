package com.ikmr.banbara23.yaeyama_liner_checker.entity;

/**
 * ステータス情報
 */
public class StatusInfo {
    Status status;
    String statusText;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "StatusInfo{" +
                "status=" + status +
                ", statusText='" + statusText + '\'' +
                '}';
    }
}
