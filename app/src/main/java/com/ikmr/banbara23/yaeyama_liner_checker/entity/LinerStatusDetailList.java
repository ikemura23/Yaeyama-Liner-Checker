package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import java.util.ArrayList;
import java.util.List;

public class LinerStatusDetailList extends LinerInfo {
    List<LinerStatusDetail> linerStatusDetails;

    public LinerStatusDetailList() {
        linerStatusDetails = new ArrayList<>();
    }

    public List<LinerStatusDetail> getLinerStatusDetails() {
        return linerStatusDetails;
    }

    public void setLinerStatusDetails(List<LinerStatusDetail> linerStatusDetails) {
        this.linerStatusDetails = linerStatusDetails;
    }

    @Override
    public String toString() {
        return "LinerStatusDetailList{" +
                "linerStatusDetails=" + linerStatusDetails +
                '}';
    }
}
