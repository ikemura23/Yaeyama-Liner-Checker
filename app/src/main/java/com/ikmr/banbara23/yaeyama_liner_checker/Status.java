
package com.ikmr.banbara23.yaeyama_liner_checker;

public enum Status {
    NORMAL("normal", "通常運航"), CANCEL("cancel", "欠航"), OTHER("other", "その他");

    String status;
    String statusName;

    Status(String status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
