package com.ikmr.banbara23.yaeyama_liner_checker.entity;

/**
 * ステータス詳細
 */
public class LinerStatusDetail extends LinerStatus {

    LinerRecordInfo linerRecordInfo;

    public LinerRecordInfo getLinerRecordInfo() {
        return linerRecordInfo;
    }

    public void setLinerRecordInfo(LinerRecordInfo linerRecordInfo) {
        this.linerRecordInfo = linerRecordInfo;
    }

    @Override
    public String toString() {
        return "LinerStatusDetail{" +
                ", linerRecordInfo=" + linerRecordInfo +
                '}';
    }
}
